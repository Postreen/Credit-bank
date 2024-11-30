package com.calculator.service.scoring;

import lombok.RequiredArgsConstructor;
import com.calculator.dto.request.ScoringDataDto;
import com.calculator.dto.utils.RateAndInsuredServiceDto;
import com.calculator.dto.utils.SimpleScoringInfoDto;
import com.calculator.service.scoring.filter.ScoringHardFilter;
import com.calculator.service.scoring.filter.ScoringLoanFilter;
import com.calculator.service.scoring.filter.ScoringSoftFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScoringProviderImpl implements ScoringProvider {

    private final List<ScoringHardFilter> hardFilters;
    private final List<ScoringSoftFilter> softFilters;
    private final List<ScoringLoanFilter> loanFilters;

    @Value("${service.rate}")
    private BigDecimal rate;

    @Override
    public List<SimpleScoringInfoDto> simpleScoring() {

        List<SimpleScoringInfoDto> offers = new ArrayList<>();
        List<Map<String, Boolean>> allFilterCombinations = generateAllFilterCombinations();

        for (Map<String, Boolean> filterStates : allFilterCombinations) {
            List<RateAndInsuredServiceDto> filterResults = applyFilters(filterStates);

            RateAndInsuredServiceDto totalEffect = calculateTotalEffect(filterResults);
            RateAndInsuredServiceDto finalResult = combineEffects(totalEffect);

            offers.add(new SimpleScoringInfoDto(filterStates, finalResult));
        }

        log.debug("Possible offers have been calculated: {}", offers);

        return offers;
    }

    private List<Map<String, Boolean>> generateAllFilterCombinations() {
        List<Map<String, Boolean>> combinations = new ArrayList<>();
        int filterCount = loanFilters.size();
        int totalCombinations = (int) Math.pow(2, filterCount);

        for (int combinationIndex = 0; combinationIndex < totalCombinations; combinationIndex++) {
            Map<String, Boolean> currentCombination = getStringBooleanMap(filterCount, combinationIndex);

            combinations.add(currentCombination);
        }

        return combinations;
    }

    private Map<String, Boolean> getStringBooleanMap(int filterCount, int combinationIndex) {
        Map<String, Boolean> currentCombination = new HashMap<>();

        for (int i = 0; i < filterCount; i++) {
            ScoringLoanFilter filter = loanFilters.get(i);
            String filterName = filter.getClass().getSimpleName();

            boolean isActive = (combinationIndex / (int) Math.pow(2, i)) % 2 == 1;
            currentCombination.put(filterName, isActive);
        }
        return currentCombination;
    }

    private List<RateAndInsuredServiceDto> applyFilters(Map<String, Boolean> filterStates) {
        List<RateAndInsuredServiceDto> filterResults = new ArrayList<>();
        for (ScoringLoanFilter filter : loanFilters) {
            boolean isActive = filterStates.get(filter.getClass().getSimpleName());
            filterResults.add(filter.check(isActive));

            log.info("{}:change rate={}:insurance cost={}", filter.getClass().getSimpleName(),
                    filter.check(isActive).newRate(),
                    filter.check(isActive).insuredService());

        }
        return filterResults;
    }

    private RateAndInsuredServiceDto calculateTotalEffect(List<RateAndInsuredServiceDto> filterResults) {
        return filterResults.stream()
                .reduce(
                        new RateAndInsuredServiceDto(BigDecimal.ZERO, BigDecimal.ZERO),
                        (x, y) -> new RateAndInsuredServiceDto(
                                x.newRate().add(y.newRate()),
                                x.insuredService().add(y.insuredService())
                        )
                );
    }

    private RateAndInsuredServiceDto combineEffects(RateAndInsuredServiceDto totalEffect) {
        return new RateAndInsuredServiceDto(
                rate.add(totalEffect.newRate()),
                totalEffect.insuredService()
        );
    }

    @Override
    public RateAndInsuredServiceDto fullScoring(ScoringDataDto scoringDataDto) {

        log.debug("Scoring data={}, rate={}", scoringDataDto, rate);

        hardScoring(scoringDataDto);

        RateAndInsuredServiceDto rateAndInsuredServiceDto = softScoring(scoringDataDto, rate);

        log.info("Result scoring data={} is {}, insurance cost={}"
                , scoringDataDto, rateAndInsuredServiceDto.newRate(), rateAndInsuredServiceDto.insuredService());

        return rateAndInsuredServiceDto;
    }

    @Override
    public void hardScoring(ScoringDataDto scoringDataDto) {
        hardFilters.stream()
                .allMatch(filter -> filter.check(scoringDataDto));
    }

    @Override
    public RateAndInsuredServiceDto softScoring(ScoringDataDto scoringDataDto, BigDecimal rate) {

        List<RateAndInsuredServiceDto> resultList = softFilters.stream()
                .map(filter -> filter.check(scoringDataDto))
                .toList();

        BigDecimal diffRate = resultList.stream()
                .map(RateAndInsuredServiceDto::newRate)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal insuredService = resultList.stream()
                .map(RateAndInsuredServiceDto::insuredService)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        RateAndInsuredServiceDto rateAndInsuredServiceDto = new RateAndInsuredServiceDto(rate.add(diffRate), insuredService);

        return rateAndInsuredServiceDto;
    }
}
