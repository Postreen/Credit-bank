package calculator.service.scoring.filter.soft;


import calculator.dto.enums.Gender;
import calculator.dto.request.ScoringDataDto;
import calculator.dto.utils.RateAndInsuredServiceDto;
import calculator.service.scoring.filter.ScoringSoftFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class GenderAndAgeSoftScoringFilter implements ScoringSoftFilter {

    @Value("${scoring.filters.soft.gender.femaleAge.min}")
    private Integer minAgeFemale;
    @Value("${scoring.filters.soft.gender.femaleAge.max}")
    private Integer maxAgeFemale;

    @Value("${scoring.filters.soft.gender.maleAge.min}")
    private Integer minAgeMale;
    @Value("${scoring.filters.soft.gender.maleAge.max}")
    private Integer maxAgeMale;

    @Value("${scoring.filters.soft.gender.changeRate}")
    private BigDecimal changeRateNormalGenderValue;
    @Value("${scoring.filters.soft.gender.notBinary.changeRate}")
    private BigDecimal changeRateNotBinaryValue;

    @Override
    public RateAndInsuredServiceDto check(ScoringDataDto scoringDataDto) {
        Gender gender = scoringDataDto.gender();
        long age = ChronoUnit.YEARS.between(scoringDataDto.birthdate(), LocalDate.now());

        if (gender == Gender.FEMALE && (age >= minAgeFemale && age <= maxAgeFemale)
                || gender == Gender.MALE && (age >= minAgeMale && age <= maxAgeMale)) {
            return new RateAndInsuredServiceDto(changeRateNormalGenderValue, BigDecimal.ZERO);
        } else if (gender == Gender.NOT_BINARY) {
            return new RateAndInsuredServiceDto(changeRateNotBinaryValue, BigDecimal.ZERO);
        }
        return new RateAndInsuredServiceDto(BigDecimal.ZERO, BigDecimal.ZERO);
    }
}
