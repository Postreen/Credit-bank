package calculator.dto.utils;

import java.math.BigDecimal;

public record RateAndInsuredServiceDto(
        BigDecimal newRate, //новая ставка
        BigDecimal insuredService //стоимость страховки
) {}