package com.deal.entity;

import com.deal.utils.enums.Gender;
import com.deal.utils.enums.MaritalStatus;
import com.deal.utils.json.Employment;
import com.deal.utils.json.Passport;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "clients")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "client_id")
    private UUID clientId;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "birth_date", columnDefinition = "DATE")
    private LocalDate birthdate;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status")
    private MaritalStatus maritalStatus;

    @Column(name = "dependent_amount")
    private Integer dependentAmount;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "passport_id", columnDefinition = "jsonb")
    private Passport passport;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "employment_id", columnDefinition = "jsonb")
    private Employment employment;

    @Column(name = "account_number")
    private String accountNumber;
}
