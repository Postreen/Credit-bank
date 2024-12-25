package com.deal;

import com.deal.dto.request.EmploymentDto;
import com.deal.dto.request.FinishRegistrationRequestDto;
import com.deal.dto.request.LoanStatementRequestDto;
import com.deal.dto.request.ScoringDataDto;
import com.deal.dto.response.CreditDto;
import com.deal.dto.response.LoanOfferDto;
import com.deal.dto.response.PaymentScheduleElementDto;
import com.deal.entity.Client;
import com.deal.entity.Credit;
import com.deal.entity.Statement;
import com.deal.utils.enums.*;
import com.deal.utils.json.Employment;
import com.deal.utils.json.Passport;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TestUtils {

    public static final String LOAN_OFFERS_ENDPOINT_CALCULATOR = "/v1/calculator/offers";
    public static final String STATEMENT_ENDPOINT_DEAL = "/v1/deal/statement";
    public static final String CALC_CREDIT_ENDPOINT_CALCULATOR = "/v1/calculator/calc";

    public static ScoringDataDto getScoringDataDto() {
        return new ScoringDataDto(
                BigDecimal.valueOf(30_000),
                12,
                "Alfred",
                "Chelicocowich",
                "Nikitiwich",
                Gender.MALE,
                "Chelicocowich@gmail.com",
                LocalDate.of(2000, 11, 21),
                "5555",
                "567890",
                LocalDate.of(2015, 11, 21),
                "ГУ МВД РОССИИ",
                MaritalStatus.SINGLE,
                27,
                new EmploymentDto(
                        EmploymentStatus.SELF_EMPLOYED,
                        "123456789012345678",
                        BigDecimal.valueOf(50_000),
                        EmploymentPosition.TOP_MANAGER,
                        19,
                        4
                ),
                "79873022923",
                false,
                false);
    }

    public static LoanStatementRequestDto getLoanStatementRequestDto() {
        return new LoanStatementRequestDto(
                new BigDecimal("30000"),
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

    public static LoanStatementRequestDto getLoanStatementRequestDtoInvalidAmount() {
        return new LoanStatementRequestDto(
                new BigDecimal("11000"),
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

    public static List<LoanOfferDto> getAnnuitentPaymentListLoanOffersDtoAmount30_000Term12() {
        return List.of(
                getAnnuitentPaymentLoanOfferDtoAmount30_000Term12(),
                getAnnuitentPaymentLoanOfferDtoAmount30_000Term12Insurance(),
                getAnnuitentPaymentLoanOfferDtoAmount30_000Term12SalaryClient(),
                getAnnuitentPaymentLoanOfferDtoAmount30_000Term12NotSalaryClentAndNotInsurance()
        );
    }

    public static LoanOfferDto getAnnuitentPaymentLoanOfferDtoAmount30_000Term12NotSalaryClentAndNotInsurance() {
        return new LoanOfferDto(
                UUID.randomUUID(),
                new BigDecimal("30000"),
                new BigDecimal("33520.92"),
                12,
                new BigDecimal("2793.41"),
                new BigDecimal("21"),
                false,
                false
        );
    }

    public static LoanOfferDto getAnnuitentPaymentLoanOfferDtoAmount30_000Term12SalaryClient() {
        return new LoanOfferDto(
                UUID.randomUUID(),
                new BigDecimal("30000"),
                new BigDecimal("44006.40"),
                12,
                new BigDecimal("3667.20"),
                new BigDecimal("18"),
                true,
                false
        );
    }

    public static LoanOfferDto getAnnuitentPaymentLoanOfferDtoAmount30_000Term12Insurance() {
        return new LoanOfferDto(
                UUID.randomUUID(),
                new BigDecimal("30000"),
                new BigDecimal("33176.40"),
                12,
                new BigDecimal("2764.70"),
                new BigDecimal("19"),
                false,
                true
        );
    }

    public static LoanOfferDto getAnnuitentPaymentLoanOfferDtoAmount30_000Term12() {
        return new LoanOfferDto(
                UUID.randomUUID(),
                new BigDecimal("10000"),
                new BigDecimal("43550.76"),
                12,
                new BigDecimal("3629.23"),
                new BigDecimal("16"),
                true,
                true
        );
    }

    public static Client getPartClient() {
        return Client.builder()
                .firstName("Alfred")
                .lastName("Chelicocowich")
                .middleName("Nikitiwich")
                .birthdate(LocalDate.of(2000, 11, 21))
                .email("Chelicocowich@gmail.com")
                .passport(Passport.builder()
                        .series("5555")
                        .number("555555")
                        .build())
                .build();
    }

    public static Client getClient() {
        return Client.builder()
                .clientId(UUID.randomUUID())
                .lastName("Chelicocowich")
                .firstName("Alfred")
                .middleName("Nikitiwich")
                .birthdate(LocalDate.of(2000, 11, 21))
                .email("Chelicocowich@gmail.com")
                .gender(Gender.MALE)
                .maritalStatus(MaritalStatus.SINGLE)
                .dependentAmount(27)
                .passport(Passport.builder()
                        .passportUUID(UUID.randomUUID())
                        .series("5555")
                        .number("567890")
                        .issueBranch("ГУ МВД РОССИИ")
                        .issueDate(LocalDate.of(2015, 11, 21))
                        .build())
                .employment(Employment.builder()
                        .inn("123456789012345678")
                        .salary(BigDecimal.valueOf(50_000))
                        .status(EmploymentStatus.SELF_EMPLOYED)
                        .position(EmploymentPosition.TOP_MANAGER)
                        .workExperienceCurrent(4)
                        .workExperienceTotal(19)
                        .build())
                .accountNumber("79873022923")
                .build();
    }

    public static CreditDto getCreditDto() {
        return new CreditDto(
                new BigDecimal("30000"),
                12,
                new BigDecimal("2793.41"),
                new BigDecimal("21"),
                new BigDecimal("33520.94"),
                false,
                false,
                List.of(new PaymentScheduleElementDto(
                                1,
                                LocalDate.of(2024, 6, 27),
                                new BigDecimal("926.35"),
                                new BigDecimal("166.67"),
                                new BigDecimal("759.68"),
                                new BigDecimal("9240.32")
                        ),
                        new PaymentScheduleElementDto(
                                2,
                                LocalDate.of(2024, 7, 27),
                                new BigDecimal("926.35"),
                                new BigDecimal("154.01"),
                                new BigDecimal("772.34"),
                                new BigDecimal("8467.98")
                        ),
                        new PaymentScheduleElementDto(
                                3,
                                LocalDate.of(2024, 8, 27),
                                new BigDecimal("926.35"),
                                new BigDecimal("141.13"),
                                new BigDecimal("785.21"),
                                new BigDecimal("7682.77")
                        ),
                        new PaymentScheduleElementDto(
                                4,
                                LocalDate.of(2024, 9, 27),
                                new BigDecimal("926.35"),
                                new BigDecimal("128.05"),
                                new BigDecimal("798.30"),
                                new BigDecimal("6884.47")
                        ),
                        new PaymentScheduleElementDto(
                                5,
                                LocalDate.of(2024, 10, 27),
                                new BigDecimal("926.35"),
                                new BigDecimal("114.74"),
                                new BigDecimal("811.60"),
                                new BigDecimal("6072.87")
                        ),
                        new PaymentScheduleElementDto(
                                6,
                                LocalDate.of(2024, 11, 27),
                                new BigDecimal("926.35"),
                                new BigDecimal("101.21"),
                                new BigDecimal("825.13"),
                                new BigDecimal("5247.74")
                        ),
                        new PaymentScheduleElementDto(
                                7,
                                LocalDate.of(2024, 12, 27),
                                new BigDecimal("926.35"),
                                new BigDecimal("87.46"),
                                new BigDecimal("838.88"),
                                new BigDecimal("4408.85")
                        ),
                        new PaymentScheduleElementDto(
                                8,
                                LocalDate.of(2025, 1, 27),
                                new BigDecimal("926.35"),
                                new BigDecimal("73.48"),
                                new BigDecimal("852.86"),
                                new BigDecimal("3555.99")
                        ),
                        new PaymentScheduleElementDto(
                                9,
                                LocalDate.of(2025, 2, 27),
                                new BigDecimal("926.35"),
                                new BigDecimal("59.27"),
                                new BigDecimal("867.08"),
                                new BigDecimal("2688.91")
                        ),
                        new PaymentScheduleElementDto(
                                10,
                                LocalDate.of(2025, 3, 27),
                                new BigDecimal("926.35"),
                                new BigDecimal("44.82"),
                                new BigDecimal("881.53"),
                                new BigDecimal("1807.38")
                        ),
                        new PaymentScheduleElementDto(
                                11,
                                LocalDate.of(2025, 4, 27),
                                new BigDecimal("926.35"),
                                new BigDecimal("30.12"),
                                new BigDecimal("896.22"),
                                new BigDecimal("911.16")
                        ),
                        new PaymentScheduleElementDto(
                                12,
                                LocalDate.of(2025, 5, 27),
                                new BigDecimal("926.35"),
                                new BigDecimal("15.19"),
                                new BigDecimal("911.16"),
                                new BigDecimal("0.00")
                        ))
        );
    }

    public static Credit getCredit() {
        CreditDto creditDto = getCreditDto();
        return Credit.builder()
                .psk(creditDto.psk())
                .rate(creditDto.rate())
                .term(creditDto.term())
                .amount(creditDto.amount())
                .insuranceEnabled(creditDto.isInsuranceEnabled())
                .monthlyPayment(creditDto.monthlyPayment())
                .salaryClient(creditDto.isSalaryClient())
                .paymentSchedule(creditDto.paymentSchedule())
                .build();
    }

    public static Statement getStatementPersistent() {
        UUID id = UUID.randomUUID();
        return Statement.builder()
                .statementId(UUID.randomUUID())
                .client(getClientPersistent())
                .appliedOffer(getLoanOfferDtoPersistent(id))
                .code("test")
                .status(ApplicationStatus.PREAPPROVAL)
                .creationDate(LocalDateTime.now())
                .signDate(LocalDateTime.now())
                .build();
    }

    public static Client getClientPersistent() {
        return Client.builder()
                .clientId(UUID.randomUUID())
                .firstName("Alfred")
                .lastName("Chelicocowich")
                .middleName("Nikitiwich")
                .birthdate(LocalDate.of(2000, 11, 21))
                .email("Chelicocowich@gmail.com")
                .passport(Passport.builder()
                        .series("5555")
                        .number("567890")
                        .build())
                .build();
    }

    public static Statement getStatementTransient() {
        return Statement.builder()
                .appliedOffer(getLoanOfferDtoPersistent(UUID.randomUUID()))
                .code("test")
                .status(ApplicationStatus.PREAPPROVAL)
                .creationDate(LocalDateTime.now())
                .signDate(LocalDateTime.now())
                .build();
    }

    public static Statement getStatementPersistentError() {
        UUID statementId = UUID.randomUUID();
        return Statement.builder()
                .statementId(statementId)
                .appliedOffer(getLoanOffer(statementId))
                .code("test")
                .status(ApplicationStatus.APPROVED)
                .creationDate(LocalDateTime.now())
                .signDate(LocalDateTime.now())
                .build();
    }

    public static Statement getStatement() {
        UUID id = UUID.randomUUID();
        return Statement.builder()
                .statementId(UUID.randomUUID())
                .appliedOffer(getLoanOfferDtoPersistent(id))
                .code("test")
                .status(ApplicationStatus.PREAPPROVAL)
                .creationDate(LocalDateTime.now())
                .signDate(LocalDateTime.now())
                .build();
    }


    private static LoanOfferDto getLoanOfferDtoPersistent(UUID id) {
        return new LoanOfferDto(
                id,
                new LoanOfferDto(
                        null,
                        new BigDecimal("30000"),
                        new BigDecimal("10000"),
                        12,
                        new BigDecimal("1100"),
                        new BigDecimal("12"),
                        false,
                        false
                )
        );
    }

    public static FinishRegistrationRequestDto getFinishRegistrationRequestDto() {
        return new FinishRegistrationRequestDto(
                Gender.MALE,
                MaritalStatus.SINGLE,
                27,
                LocalDate.of(2015, 11, 21),
                "ГУ МВД РОССИИ",
                new EmploymentDto(
                        EmploymentStatus.SELF_EMPLOYED,
                        "123456789012345678",
                        new BigDecimal("50000"),
                        EmploymentPosition.TOP_MANAGER,
                        19,
                        4
                ),
                "79873022923"
        );
    }

    public static String getErrorMessageInvalidAmount() {
        return new String("amount: prescoring error");
    }

    public static LoanOfferDto getLoanOffer(UUID uuidStatement) {
        return new LoanOfferDto(
                uuidStatement,
                new BigDecimal("20000"),
                new BigDecimal("21889.20"),
                12,
                new BigDecimal("1824.10"),
                new BigDecimal("17"),
                true,
                true
        );
    }
}
