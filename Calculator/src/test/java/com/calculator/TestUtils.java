package com.calculator;

import com.calculator.dto.enums.EmploymentStatus;
import com.calculator.dto.enums.Gender;
import com.calculator.dto.enums.MaritalStatus;
import com.calculator.dto.enums.Position;
import com.calculator.dto.request.EmploymentDto;
import com.calculator.dto.request.LoanStatementRequestDto;
import com.calculator.dto.request.ScoringDataDto;
import com.calculator.dto.response.CreditDto;
import com.calculator.dto.response.LoanOfferDto;
import com.calculator.dto.response.PaymentScheduleElementDto;
import com.calculator.dto.utils.RateAndInsuredServiceDto;
import com.calculator.dto.utils.SimpleScoringInfoDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TestUtils {
    private static final String nameSalaryFilter = "SalaryClientSoftScoringFilter";
    private static final String nameInsuranceFilter = "InsuranceSoftScoringFilter";

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
                        Position.TOP_MANAGER,
                        19,
                        4
                ),
                "79873022923",
                false,
                false);
    }

    public static ScoringDataDto getScoringDataDtoNotBinaryGender() {
        return new ScoringDataDto(
                BigDecimal.valueOf(30_000),
                12,
                "Alfred",
                "Chelicocowich",
                "Nikitiwich",
                Gender.NOT_BINARY,
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
                        Position.MIDDLE_MANAGER,
                        19,
                        4
                ),
                "79873022923",
                false,
                false);
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

    public static ScoringDataDto getScoringDataDtoLessMinAge() {
        return new ScoringDataDto(
                BigDecimal.valueOf(30_000),
                12,
                "Alfred",
                "Chelicocowich",
                "Nikitiwich",
                Gender.MALE,
                "Chelicocowich@gmail.com",
                LocalDate.of(2015, 5, 26),
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
                        Position.MIDDLE_MANAGER,
                        19,
                        4
                ),
                "79873022923",
                false,
                false);
    }

    public static ScoringDataDto getScoringDataDtoGreaterMaxAge() {
        return new ScoringDataDto(
                BigDecimal.valueOf(30_000),
                12,
                "Alfred",
                "Chelicocowich",
                "Nikitiwich",
                Gender.MALE,
                "Chelicocowich@gmail.com",
                LocalDate.of(1488, 11, 21),
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
                        Position.MIDDLE_MANAGER,
                        19,
                        4
                ),
                "79873022923",
                false,
                false);
    }

    public static ScoringDataDto getScoringDataDtoUnemployedWorkStatus() {
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
                        EmploymentStatus.UNEMPLOYED,
                        "123456789012345678",
                        BigDecimal.valueOf(50_000),
                        Position.MIDDLE_MANAGER,
                        19,
                        4
                ),
                "79873022923",
                false,
                false);
    }
    public static ScoringDataDto getScoringDataDtoBusinessmanWorkStatus() {
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
                        EmploymentStatus.BUSINESSMAN,
                        "123456789012345678",
                        BigDecimal.valueOf(50_000),
                        Position.MIDDLE_MANAGER,
                        19,
                        4
                ),
                "79873022923",
                false,
                false);
    }

    public static ScoringDataDto getScoringDataDtoLessTotalWorkExperience() {
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
                        Position.MIDDLE_MANAGER,
                        10,
                        4
                ),
                "79873022923",
                false,
                false);
    }

    public static ScoringDataDto getScoringDataDtoLessCurrentWorkExperience() {
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
                        Position.MIDDLE_MANAGER,
                        19,
                        2
                ),
                "79873022923",
                false,
                false);
    }

    public static ScoringDataDto getScoringDataDtoLessAmount() {
        return new ScoringDataDto(
                BigDecimal.valueOf(50_000_000),
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
                        BigDecimal.valueOf(30_000),
                        Position.MIDDLE_MANAGER,
                        19,
                        4
                ),
                "79873022923",
                false,
                false);
    }

    public static ScoringDataDto getScoringDataDtoGreaterMaxAgeFemale() {
        return new ScoringDataDto(
                BigDecimal.valueOf(30_000_000),
                12,
                "Alfred",
                "Chelicocowich",
                "Nikitiwich",
                Gender.FEMALE,
                "Chelicocowich@gmail.com",
                LocalDate.of(1940, 5, 26),
                "5555",
                "567890",
                LocalDate.of(2015, 11, 21),
                "ГУ МВД РОССИИ",
                MaritalStatus.SINGLE,
                27,
                new EmploymentDto(
                        EmploymentStatus.SELF_EMPLOYED,
                        "123456789012345678",
                        BigDecimal.valueOf(30_000),
                        Position.MIDDLE_MANAGER,
                        19,
                        4
                ),
                "79873022923",
                false,
                false);
    }

    public static ScoringDataDto getScoringDataDtoLessMinAgeFemale() {
        return new ScoringDataDto(
                BigDecimal.valueOf(30_000_000),
                12,
                "Alfred",
                "Chelicocowich",
                "Nikitiwich",
                Gender.FEMALE,
                "Chelicocowich@gmail.com",
                LocalDate.of(2010, 5, 26),
                "5555",
                "567890",
                LocalDate.of(2015, 11, 21),
                "ГУ МВД РОССИИ",
                MaritalStatus.SINGLE,
                27,
                new EmploymentDto(
                        EmploymentStatus.SELF_EMPLOYED,
                        "123456789012345678",
                        BigDecimal.valueOf(30_000),
                        Position.MIDDLE_MANAGER,
                        19,
                        4
                ),
                "79873022923",
                false,
                false);
    }

    public static ScoringDataDto getScoringDataDtoOfTopManager() {
        return new ScoringDataDto(
                BigDecimal.valueOf(30_000),
                12,
                "Alfred",
                "Chelicocowich",
                "Nikitiwich",
                Gender.MALE,
                "Chelicocowich@gmail.com",
                LocalDate.of(1999, 5, 26),
                "5555",
                "567890",
                LocalDate.of(2015, 11, 21),
                "ГУ МВД РОССИИ",
                MaritalStatus.SINGLE,
                27,
                new EmploymentDto(
                        EmploymentStatus.BUSINESSMAN,
                        "123456789012345678",
                        BigDecimal.valueOf(50_000),
                        Position.TOP_MANAGER,
                        19,
                        4
                ),
                "79873022923",
                false,
                false);
    }

    public static ScoringDataDto getScoringDataDtoOfMan34years() {
        return new ScoringDataDto(
                BigDecimal.valueOf(30_000),
                12,
                "Alfred",
                "Chelicocowich",
                "Nikitiwich",
                Gender.MALE,
                "Chelicocowich@gmail.com",
                LocalDate.of(1990, 5, 26),
                "5555",
                "567890",
                LocalDate.of(2015, 11, 21),
                "ГУ МВД РОССИИ",
                MaritalStatus.SINGLE,
                27,
                new EmploymentDto(
                        EmploymentStatus.BUSINESSMAN,
                        "123456789012345678",
                        BigDecimal.valueOf(50_000),
                        Position.TOP_MANAGER,
                        19,
                        4
                ),
                "79873022923",
                false,
                false);
    }

    public static ScoringDataDto getScoringDataDtoOfUnknown() {
        return new ScoringDataDto(
                BigDecimal.valueOf(30_000),
                12,
                "Alfred",
                "Chelicocowich",
                "Nikitiwich",
                Gender.MALE,
                "Chelicocowich@gmail.com",
                LocalDate.of(1999, 5, 26),
                "5555",
                "567890",
                LocalDate.of(2015, 11, 21),
                "ГУ МВД РОССИИ",
                MaritalStatus.SINGLE,
                27,
                new EmploymentDto(
                        EmploymentStatus.BUSINESSMAN,
                        "123456789012345678",
                        BigDecimal.valueOf(50_000),
                        Position.UNKNOWN,
                        19,
                        4
                ),
                "79873022923",
                false,
                false);
    }

    public static ScoringDataDto getScoringDataDtoMiddleManager() {
        return new ScoringDataDto(
                BigDecimal.valueOf(30_000),
                12,
                "Alfred",
                "Chelicocowich",
                "Nikitiwich",
                Gender.FEMALE,
                "Chelicocowich@gmail.com",
                LocalDate.of(2005, 5, 26),
                "5555",
                "567890",
                LocalDate.of(2015, 11, 21),
                "ГУ МВД РОССИИ",
                MaritalStatus.MARRIED,
                27,
                new EmploymentDto(
                        EmploymentStatus.SELF_EMPLOYED,
                        "123456789012345678",
                        BigDecimal.valueOf(50_000),
                        Position.MIDDLE_MANAGER,
                        19,
                        4
                ),
                "79873022923",
                false,
                false);
    }

    public static ScoringDataDto getScoringDataDtoMilf() {
        return new ScoringDataDto(
                BigDecimal.valueOf(30_000),
                12,
                "Alfred",
                "Chelicocowich",
                "Nikitiwich",
                Gender.FEMALE,
                "Chelicocowich@gmail.com",
                LocalDate.of(1980, 5, 26),
                "5555",
                "567890",
                LocalDate.of(2015, 11, 21),
                "ГУ МВД РОССИИ",
                MaritalStatus.MARRIED,
                27,
                new EmploymentDto(
                        EmploymentStatus.SELF_EMPLOYED,
                        "123456789012345678",
                        BigDecimal.valueOf(50_000),
                        Position.MIDDLE_MANAGER,
                        19,
                        4
                ),
                "79873022923",
                false,
                false);
    }

    public static ScoringDataDto getScoringDataDtoUnknown() {
        return new ScoringDataDto(
                BigDecimal.valueOf(30_000),
                12,
                "Alfred",
                "Chelicocowich",
                "Nikitiwich",
                Gender.UNKNOWN,
                "Chelicocowich@gmail.com",
                LocalDate.of(2000, 5, 26),
                "5555",
                "567890",
                LocalDate.of(2015, 11, 21),
                "ГУ МВД РОССИИ",
                MaritalStatus.UNKNOWN,
                0,
                new EmploymentDto(
                        EmploymentStatus.UNKNOWN,
                        "123456789012345678",
                        BigDecimal.ZERO,
                        Position.UNKNOWN,
                        19,
                        0
                ),
                "79873022923",
                false,
                false);
    }

    public static ScoringDataDto getScoringDataDtoMiddleManagerWithInsurance() {
        return new ScoringDataDto(
                BigDecimal.valueOf(30_000),
                12,
                "Alfred",
                "Chelicocowich",
                "Nikitiwich",
                Gender.FEMALE,
                "Chelicocowich@gmail.com",
                LocalDate.of(1990, 5, 26),
                "5555",
                "567890",
                LocalDate.of(2015, 11, 21),
                "ГУ МВД РОССИИ",
                MaritalStatus.SINGLE,
                27,
                new EmploymentDto(
                        EmploymentStatus.BUSINESSMAN,
                        "123456789012345678",
                        BigDecimal.valueOf(50_000),
                        Position.TOP_MANAGER,
                        19,
                        4
                ),
                "79873022923",
                true,
                true);
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

    public static List<LoanOfferDto> getDifferentPaymentListLoanOffersDtoAmount30_000Term12() {
        return List.of(
                getDifferentPaymentLoanOfferDtoAmount30_000Term12NotSalaryClentAndNotInsurance(),
                getDifferentPaymentLoanOfferDtoAmount30_000Term12SalaryClient(),
                getDifferentPaymentLoanOfferDtoAmount30_000Term12Insurance(),
                getDifferentPaymentLoanOfferDtoAmount30_000Term12()
        );
    }

    public static LoanOfferDto getDifferentPaymentLoanOfferDtoAmount30_000Term12NotSalaryClentAndNotInsurance() {
        return new LoanOfferDto(
                UUID.randomUUID(),
                new BigDecimal("10000"),
                new BigDecimal("11086.03"),
                12,
                new BigDecimal("920.00"),
                new BigDecimal("20"),
                false,
                false
        );
    }

    public static LoanOfferDto getDifferentPaymentLoanOfferDtoAmount30_000Term12SalaryClient() {
        return new LoanOfferDto(
                UUID.randomUUID(),
                new BigDecimal("10000"),
                new BigDecimal("10977.44"),
                12,
                new BigDecimal("910.00"),
                new BigDecimal("18"),
                false,
                true
        );
    }

    public static LoanOfferDto getDifferentPaymentLoanOfferDtoAmount30_000Term12Insurance() {
        return new LoanOfferDto(
                UUID.randomUUID(),
                new BigDecimal("10000"),
                new BigDecimal("22063.60"),
                12,
                new BigDecimal("1800.00"),
                new BigDecimal("19"),
                true,
                false
        );
    }

    public static LoanOfferDto getDifferentPaymentLoanOfferDtoAmount30_000Term12() {
        return new LoanOfferDto(
                UUID.randomUUID(),
                new BigDecimal("10000"),
                new BigDecimal("21846.36"),
                12,
                new BigDecimal("1800.00"),
                new BigDecimal("17"),
                true,
                true
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
                new BigDecimal("33176.40"),
                12,
                new BigDecimal("2764.70"),
                new BigDecimal("19"),
                false,
                true
        );
    }

    public static LoanOfferDto getAnnuitentPaymentLoanOfferDtoAmount30_000Term12Insurance() {
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

    public static LoanOfferDto getAnnuitentPaymentLoanOfferDtoAmount30_000Term12() {
        return new LoanOfferDto(
                UUID.randomUUID(),
                new BigDecimal("30000"),
                new BigDecimal("43550.76"),
                12,
                new BigDecimal("3629.23"),
                new BigDecimal("16"),
                true,
                true
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
                "Chelicocowich_gmail.com",
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
                "5555555555",
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

    public static List<SimpleScoringInfoDto> getSimpleScoringInfoDto() {
        return List.of(
                getSimpleScoringInfoDtoSalaryAndInsurance(),
                getSimpleScoringInfoDtoInsurance(),
                getSimpleScoringInfoDtoSalaryClient(),
                getSimpleScoringInfoDtoNotSalaryClientAndNotInsurance()
        );
    }

    public static SimpleScoringInfoDto getSimpleScoringInfoDtoSalaryClient() {
        return new SimpleScoringInfoDto(
                Map.of(nameSalaryFilter, true,
                        nameInsuranceFilter, false),
                new RateAndInsuredServiceDto(new BigDecimal("19"), BigDecimal.ZERO)
        );
    }

    public static SimpleScoringInfoDto getSimpleScoringInfoDtoNotSalaryClientAndNotInsurance() {
        return new SimpleScoringInfoDto(
                Map.of(nameSalaryFilter, false,
                        nameInsuranceFilter, false),
                new RateAndInsuredServiceDto(new BigDecimal("21"), BigDecimal.ZERO)
        );
    }

    public static SimpleScoringInfoDto getSimpleScoringInfoDtoInsurance() {
        return new SimpleScoringInfoDto(
                Map.of(nameSalaryFilter, false,
                        nameInsuranceFilter, true),
                new RateAndInsuredServiceDto(new BigDecimal("18"), new BigDecimal("10000"))
        );
    }

    public static SimpleScoringInfoDto getSimpleScoringInfoDtoSalaryAndInsurance() {
        return new SimpleScoringInfoDto(
                Map.of(nameSalaryFilter, true,
                        nameInsuranceFilter, true),
                new RateAndInsuredServiceDto(new BigDecimal("16"), new BigDecimal("10000"))
        );
    }
}