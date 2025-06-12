package com.calculator.service.scoring.filter.soft;

import com.calculator.dto.request.ScoringDataDto;
import com.calculator.dto.utils.RateAndInsuredServiceDto;
import com.calculator.service.scoring.filter.ScoringSoftFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class WorkStatusSoftScoringFilter implements ScoringSoftFilter {

    @Value("${scoring.filters.soft.workStatus.selfEmployed.changeRate}")
    private BigDecimal changeRateValueSelfEmployed;
    @Value("${scoring.filters.soft.workStatus.businessman.changeRate}")
    private BigDecimal changeRateValueBusinessman;

    /**
     * Проверяет статус занятости клиента и возвращает соответствующую корректировку процентной ставки.
     *
     * @param scoringDataDto Объект, содержащий данные клиента для скоринга, включая информацию о занятости.
     * @return RateAndInsuredServiceDto объект, содержащий скорректированную процентную ставку и стоимость страховки.
     *
     * Метод анализирует статус занятости клиента из переданных данных скоринга:
     * - Для самозанятых (SELF_EMPLOYED) применяется корректировка ставки changeRateValueSelfEmployed.
     * - Для предпринимателей (BUSINESSMAN) применяется корректировка ставки changeRateValueBusinessman.
     * - Для всех остальных статусов занятости корректировка ставки не производится (возвращается нулевое значение).
     *
     * Стоимость страховки всегда устанавливается в ноль (BigDecimal.ZERO) для всех случаев.
     *
     * Значения корректировок ставок загружаются из конфигурации приложения с использованием аннотации @Value.
     */
    @Override
    public RateAndInsuredServiceDto check(ScoringDataDto scoringDataDto) {
        switch (scoringDataDto.employment().employmentStatus()) {
            case SELF_EMPLOYED -> {
                return new RateAndInsuredServiceDto(changeRateValueSelfEmployed, BigDecimal.ZERO);
            }
            case BUSINESSMAN -> {
                return new RateAndInsuredServiceDto(changeRateValueBusinessman, BigDecimal.ZERO);
            }
            default -> {
                return new RateAndInsuredServiceDto(BigDecimal.ZERO, BigDecimal.ZERO);
            }
        }
    }
}

