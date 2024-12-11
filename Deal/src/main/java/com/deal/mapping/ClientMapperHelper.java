package com.deal.mapping;

import com.deal.dto.request.EmploymentDto;
import com.deal.dto.request.FinishRegistrationRequestDto;
import com.deal.utils.Client;
import com.deal.utils.json.Employment;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ClientMapperHelper {

    public void updateClientWithFinishRegistration(Client client, FinishRegistrationRequestDto finishRegistration) {
        if (client == null || finishRegistration == null) {
            return;
        }

        client.setGender(finishRegistration.gender());
        client.setMaritalStatus(finishRegistration.maritalStatus());
        client.setDependentAmount(finishRegistration.dependentAmount());
        client.setEmployment(employmentDtoToEmployment(finishRegistration.employment()));
        client.setAccountNumber(finishRegistration.accountNumber());
    }

    private Employment employmentDtoToEmployment(EmploymentDto employmentDto) {
        if (employmentDto == null) {
            return null;
        }

        Employment employment = new Employment();

        employment.setEmploymentUUID(UUID.randomUUID());
        employment.setStatus(employmentDto.employmentStatus());
        employment.setInn(employmentDto.employerINN());
        employment.setSalary(employmentDto.salary());
        employment.setPosition(employmentDto.position());
        employment.setWorkExperienceTotal(employmentDto.workExperienceTotal());
        employment.setWorkExperienceCurrent(employmentDto.workExperienceCurrent());

        return employment;
    }
}