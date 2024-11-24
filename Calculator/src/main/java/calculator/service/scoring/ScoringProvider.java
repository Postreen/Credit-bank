package calculator.service.scoring;

import lombok.RequiredArgsConstructor;
import calculator.dto.request.ScoringDataDto;
import calculator.dto.utils.RateAndInsuredServiceDto;
import calculator.dto.utils.SimpleScoringInfoDto;
import calculator.exceptions.ScoringException;
import calculator.service.scoring.filter.ScoringHardFilter;
import calculator.service.scoring.filter.ScoringLoanFilter;
import calculator.service.scoring.filter.ScoringSoftFilter;
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
public class ScoringProvider implements ScoringProviderImpl {

    private final List<ScoringHardFilter> hardFilters;
    private final List<ScoringSoftFilter> softFilters;
    private final List<ScoringLoanFilter> loanFilters;

    @Value("${service.rate}")
    private BigDecimal rate;

    @Override
    public List<SimpleScoringInfoDto> simpleScoring() {

        log.info("Starting simple scoring...");

        int filterCount = loanFilters.size();
        List<SimpleScoringInfoDto> offers = new ArrayList<>();

        for (int mask = 0; mask < (1 << filterCount); mask++) {
            Map<String, Boolean> filterStates = generateFilterStates(mask, filterCount);
            List<RateAndInsuredServiceDto> filterResults = applyFilters(filterStates);

            RateAndInsuredServiceDto totalEffect = calculateTotalEffect(filterResults);
            RateAndInsuredServiceDto finalResult = combineEffects(totalEffect);

            offers.add(new SimpleScoringInfoDto(filterStates, finalResult));
        }

        log.debug("Possible offers have been calculated: {}", offers);
        log.info("Possible offers have been calculated");

        return offers;
    }

    private Map<String, Boolean> generateFilterStates(int mask, int filterCount) {
        Map<String, Boolean> filterStates = new HashMap<>();
        for (int i = 0; i < filterCount; i++) {
            ScoringLoanFilter filter = loanFilters.get(i);
            boolean isActive = (mask >> i & 1) == 1;
            filterStates.put(filter.getClass().getSimpleName(), isActive);

//

        }
        return filterStates;
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

    private RateAndInsuredServiceDto combineEffects(RateAndInsuredServiceDto totalEffect) {
        return new RateAndInsuredServiceDto(
                rate.add(totalEffect.newRate()),
                totalEffect.insuredService()
        );
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

    @Override
    public RateAndInsuredServiceDto fullScoring(ScoringDataDto scoringDataDto) {

        log.info("Full scoring started...");
        log.debug("Full scoring data={}, rate={}", scoringDataDto, rate);

        if (!hardScoring(scoringDataDto)) {
            log.warn("Hard scoring failed for scoring data: {}", scoringDataDto);
            throw new ScoringException();
        }

        RateAndInsuredServiceDto rateAndInsuredServiceDto = softScoring(scoringDataDto, rate);

        log.info("Result scoring data={} is {}, insurance cost={}"
                , scoringDataDto, rateAndInsuredServiceDto.newRate(), rateAndInsuredServiceDto.insuredService());

        return rateAndInsuredServiceDto;
    }

    @Override
    public boolean hardScoring(ScoringDataDto scoringDataDto) {

        log.info("Hard scoring started...");

        boolean statusHardScoring = hardFilters.stream()
                .allMatch(filter -> filter.check(scoringDataDto));

        return statusHardScoring;
    }

    @Override
    public RateAndInsuredServiceDto softScoring(ScoringDataDto scoringDataDto, BigDecimal rate) {

        log.info("Soft scoring started...");

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
