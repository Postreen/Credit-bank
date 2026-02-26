package com.deal.mapping;

import com.deal.TestUtils;
import com.deal.dto.request.LoanStatementRequestDto;
import com.deal.dto.request.ScoringDataDto;
import com.deal.entity.Client;
import com.deal.utils.json.Employment;
import com.deal.utils.json.Passport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientMapperTests {

    @Mock
    private ClientMapper clientMapper;

    @DisplayName("Test map LoanStatementRequestDto to Client with Mocked Passport")
    @Test
    public void givenLoanStatementRequestDto_whenMapToClient_thenReturnClient() {
        LoanStatementRequestDto loanStatementRequest = TestUtils.getLoanStatementRequestDto();
        Client expectedClient = TestUtils.getPartClient();

        when(clientMapper.toClient(loanStatementRequest)).thenReturn(expectedClient);
        Client actualClient = clientMapper.toClient(loanStatementRequest);

        assertThat(actualClient)
                .usingRecursiveComparison()
                .ignoringFields("clientId", "gender", "maritalStatus",
                        "dependentAmount", "employment", "accountNumber", "passport.passportUUID",
                        "passport.issueBranch", "passport.issueDate")
                .isEqualTo(expectedClient);
    }

    @DisplayName("Test updateClientFromScoringData updates fields correctly")
    @Test
    public void testUpdateClientFromScoringData() {
        ScoringDataDto scoringDataDto = TestUtils.getScoringDataDto();
        Client expectedClient = TestUtils.getClient();

        clientMapper.updateClientFromScoringData(expectedClient, scoringDataDto);

        assertThat(expectedClient.getGender()).isEqualTo(scoringDataDto.gender());
        assertThat(expectedClient.getMaritalStatus()).isEqualTo(scoringDataDto.maritalStatus());
        assertThat(expectedClient.getDependentAmount()).isEqualTo(scoringDataDto.dependentAmount());
        assertThat(expectedClient.getAccountNumber()).isEqualTo(scoringDataDto.accountNumber());
        assertThat(expectedClient.getEmployment())
                .isNotNull()
                .extracting(Employment::getPosition)
                .isEqualTo(scoringDataDto.employment().position());
        assertThat(expectedClient.getPassport())
                .isNotNull()
                .extracting(Passport::getSeries, Passport::getNumber, Passport::getIssueBranch, Passport::getIssueDate)
                .containsExactly(
                        scoringDataDto.passportSeries(),
                        scoringDataDto.passportNumber(),
                        scoringDataDto.passportIssueBranch(),
                        scoringDataDto.passportIssueDate()
                );
    }
}

