package com.statement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.statement.dto.LoanOfferDto;
import com.statement.dto.LoanStatementRequestDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class TestUtils {
    public static final String LOAN_OFFERS_ENDPOINT_STATEMENT = "/v1/statement";


    public static Object getLoanStatementRequestDtoInvalidNullAmount() {
        return new LoanStatementRequestDto(
                null,
                12,
                "Alfred",
                "Chelicocowich",
                "Nikitiwich",
                "Chelicocowich@gmail.com",
                LocalDate.of(2000, 11, 21),
                "5555",
                "555555"
        );
    }

    public static Object getLoanStatementRequestDtoInvalidNullTerm() {
        return new LoanStatementRequestDto(
                new BigDecimal("30000"),
                null,
                "Alfred",
                "Chelicocowich",
                "Nikitiwich",
                "Chelicocowich@gmail.com",
                LocalDate.of(2000, 11, 21),
                "5555",
                "555555"
        );
    }

    public static Object getLoanStatementRequestDtoInvalidNullFirstName() {
        return new LoanStatementRequestDto(
                new BigDecimal("30000"),
                12,
                "",
                "Chelicocowich",
                "Nikitiwich",
                "Chelicocowich@gmail.com",
                LocalDate.of(2000, 11, 21),
                "5555",
                "555555"
        );
    }

    public static Object getLoanStatementRequestDtoInvalidNullLastName() {
        return new LoanStatementRequestDto(
                new BigDecimal("30000"),
                12,
                "Alfred",
                "",
                "Nikitiwich",
                "Chelicocowich@gmail.com",
                LocalDate.of(2000, 11, 21),
                "5555",
                "555555"
        );
    }

    public static Object getLoanStatementRequestDtoInvalidNullMiddleName() {
        return new LoanStatementRequestDto(
                new BigDecimal("30000"),
                12,
                "Alfred",
                "Chelicocowich",
                "",
                "Chelicocowich@gmail.com",
                LocalDate.of(2000, 11, 21),
                "5555",
                "555555"
        );
    }

    public static Object getLoanStatementRequestDtoInvalidNullEmail() {
        return new LoanStatementRequestDto(
                new BigDecimal("30000"),
                12,
                "Alfred",
                "Chelicocowich",
                "Nikitiwich",
                null,
                LocalDate.of(2000, 11, 21),
                "5555",
                "555555"
        );
    }

    public static Object getLoanStatementRequestDtoInvalidNullBirthdate() {
        return new LoanStatementRequestDto(
                new BigDecimal("30000"),
                12,
                "Alfred",
                "Chelicocowich",
                "Nikitiwich",
                "Chelicocowich@gmail.com",
                null,
                "5555",
                "555555"
        );
    }

    public static Object getLoanStatementRequestDtoInvalidNullPassportSeries() {
        return new LoanStatementRequestDto(
                new BigDecimal("30000"),
                12,
                "Alfred",
                "Chelicocowich",
                "Nikitiwich",
                "Chelicocowich@gmail.com",
                LocalDate.of(2000, 11, 21),
                "",
                "555555"
        );
    }

    public static Object getLoanStatementRequestDtoInvalidNullPassportNumber() {
        return new LoanStatementRequestDto(
                new BigDecimal("30000"),
                12,
                "Alfred",
                "Chelicocowich",
                "Nikitiwich",
                "Chelicocowich@gmail.com",
                LocalDate.of(2000, 11, 21),
                "5555",
                null
        );
    }

    public static Object getLoanOfferNullUUID() {
        return new LoanOfferDto(
                null,
                new BigDecimal("10000"),
                new BigDecimal("11116.20"),
                12,
                new BigDecimal("926.35"),
                new BigDecimal("20"),
                false,
                false
        );
    }

    public static Object getLoanOfferNullRequestAmount() {
        return new LoanOfferDto(
                UUID.randomUUID(),
                null,
                new BigDecimal("11116.20"),
                12,
                new BigDecimal("926.35"),
                new BigDecimal("20"),
                false,
                false
        );
    }

    public static Object getLoanOfferNullTotalAmount() {
        return new LoanOfferDto(
                UUID.randomUUID(),
                new BigDecimal("10000"),
                null,
                12,
                new BigDecimal("926.35"),
                new BigDecimal("20"),
                false,
                false
        );
    }

    public static Object getLoanOfferNullTerm() {
        return new LoanOfferDto(
                UUID.randomUUID(),
                new BigDecimal("10000"),
                new BigDecimal("11116.20"),
                null,
                new BigDecimal("926.35"),
                new BigDecimal("20"),
                false,
                false
        );
    }

    public static Object getLoanOfferNullMonthlyPayment() {
        return new LoanOfferDto(
                UUID.randomUUID(),
                new BigDecimal("10000"),
                new BigDecimal("11116.20"),
                12,
                null,
                new BigDecimal("20"),
                false,
                false
        );
    }

    public static LoanStatementRequestDto getLoanStatementRequestDtoInvalidAmount() {
        return new LoanStatementRequestDto(
                new BigDecimal("10000"),
                12,
                "Alfred",
                "Chelicocowich",
                "Nikitiwich",
                "Chelicocowich@gmail.com",
                LocalDate.of(2000, 11, 21),
                "5555",
                "555555"
        );
    }

    public static LoanStatementRequestDto getLoanStatementRequestDtoInvalidTerm() {
        return new LoanStatementRequestDto(
                new BigDecimal("50000"),
                3,
                "Alfred",
                "Chelicocowich",
                "Nikitiwich",
                "Chelicocowich@gmail.com",
                LocalDate.of(2000, 11, 21),
                "5555",
                "555555"
        );
    }

    public static LoanStatementRequestDto getLoanStatementRequestDtoInvalidFirstName() {
        return new LoanStatementRequestDto(
                new BigDecimal("50000"),
                12,
                "Мунир",
                "Chelicocowich",
                "Nikitiwich",
                "Chelicocowich@gmail.com",
                LocalDate.of(2000, 11, 21),
                "5555",
                "555555"
        );
    }

    public static LoanStatementRequestDto getLoanStatementRequestDtoInvalidLastName() {
        return new LoanStatementRequestDto(
                new BigDecimal("50000"),
                12,
                "Minur",
                "S",
                "Nikitiwich",
                "Chelicocowich@gmail.com",
                LocalDate.of(2000, 11, 21),
                "5555",
                "555555"
        );
    }

    public static LoanStatementRequestDto getLoanStatementRequestDtoInvalidMiddleName() {
        return new LoanStatementRequestDto(
                new BigDecimal("50000"),
                12,
                "Minur",
                "Chelicocowich",
                "Раисович",
                "Chelicocowich@gmail.com",
                LocalDate.of(2000, 11, 21),
                "5555",
                "555555"
        );
    }

    public static LoanStatementRequestDto getLoanStatementRequestDtoInvalidEmail() {
        return new LoanStatementRequestDto(
                new BigDecimal("50000"),
                12,
                "Minur",
                "Chelicocowich",
                "Nikitiwich",
                "msunchalyaev_gmail.com",
                LocalDate.of(2000, 11, 21),
                "5555",
                "555555"
        );
    }

    public static LoanStatementRequestDto getLoanStatementRequestDtoInvalidBirthdate() {
        return new LoanStatementRequestDto(
                new BigDecimal("50000"),
                12,
                "Minur",
                "Chelicocowich",
                "Nikitiwich",
                "Chelicocowich@gmail.com",
                LocalDate.of(2015, 5, 26),
                "5555",
                "555555"
        );
    }

    public static LoanStatementRequestDto getLoanStatementRequestDtoInvalidPassportSeries() {
        return new LoanStatementRequestDto(
                new BigDecimal("50000"),
                12,
                "Minur",
                "Chelicocowich",
                "Nikitiwich",
                "Chelicocowich@gmail.com",
                LocalDate.of(2000, 11, 21),
                "4618469798",
                "555555"
        );
    }

    public static LoanStatementRequestDto getLoanStatementRequestDtoInvalidPassportNumber() {
        return new LoanStatementRequestDto(
                new BigDecimal("50000"),
                12,
                "Minur",
                "Chelicocowich",
                "Nikitiwich",
                "Chelicocowich@gmail.com",
                LocalDate.of(2000, 11, 21),
                "5555",
                "46979"
        );
    }


}
