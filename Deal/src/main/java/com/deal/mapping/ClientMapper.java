package com.deal.mapping;

import com.deal.dto.request.LoanStatementRequestDto;
import com.deal.dto.request.ScoringDataDto;
import com.deal.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    @Mapping(target = "passport.series", source = "passportSeries")
    @Mapping(target = "passport.number", source = "passportNumber")
    Client toClient(LoanStatementRequestDto loanStatement);

    @Mapping(target = "gender", source = "gender")
    @Mapping(target = "maritalStatus", source = "maritalStatus")
    @Mapping(target = "dependentAmount", source = "dependentAmount")
    @Mapping(target = "client.passport.issueDate", source = "passportIssueDate")
    @Mapping(target = "client.passport.issueBranch", source = "passportIssueBranch")
    @Mapping(target = "employment", source = "employment")
    @Mapping(target = "client.employment.inn", source = "employment.employerINN")
    @Mapping(target = "client.employment.status", source = "employment.employmentStatus")
    @Mapping(target = "accountNumber", source = "accountNumber")
    void updateClientFromScoringData(@MappingTarget Client client, ScoringDataDto scoringDataDto);
}
