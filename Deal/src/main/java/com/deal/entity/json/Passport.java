package com.deal.utils.json;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Passport {
    private UUID passportUUID;
    private String series;
    private String number;
    private String issueBranch;
    private LocalDate issueDate;
}