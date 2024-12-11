package com.deal.mapping;

import com.deal.dto.request.FinishRegistrationRequestDto;
import com.deal.dto.request.ScoringDataDto;
import com.deal.utils.Statement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScoringMapper {
    @Mapping(target = "amount", source = "statement.appliedOffer.requestedAmount")
    @Mapping(target = "term", source = "statement.appliedOffer.term")
    @Mapping(target = "isInsuranceEnabled", source = "statement.appliedOffer.isInsuranceEnabled")
    @Mapping(target = "isSalaryClient", source = "statement.appliedOffer.isSalaryClient")
    @Mapping(target = "firstname", source = "statement.client.firstName")
    @Mapping(target = "lastName", source = "statement.client.lastName")
    @Mapping(target = "middleName", source = "statement.client.middleName")
    @Mapping(target = "email", source = "statement.client.email")
    @Mapping(target = "birthdate", source = "statement.client.birthdate")
    @Mapping(target = "passportSeries", source = "statement.client.passport.series")
    @Mapping(target = "passportNumber", source = "statement.client.passport.number")
    @Mapping(target = "passportIssueBranch", source = "finishRegistration.passportIssueBranch")
    ScoringDataDto toScoringDataDto(Statement statement, FinishRegistrationRequestDto finishRegistration);
}
