package com.deal.mapping;

import com.deal.TestUtils;
import com.deal.dto.request.LoanStatementRequestDto;
import com.deal.utils.Client;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ClientMapperTests {
    @Autowired
    private ClientMapper mapper;

    @DisplayName("Test map LoanStatementRequestDto to Client")
    @Test
    public void givenTestLoanStatementRequestAndExpectedClient_whenMapLoanStatementToClient_thenEquals() {
        LoanStatementRequestDto loanStatementRequest = TestUtils.getLoanStatementRequestDto();
        Client expectedClient = TestUtils.getPartClient();

        Client actualClient = mapper.toClient(loanStatementRequest);

        assertThat(actualClient)
                .usingRecursiveComparison()
                .ignoringFields("clientId", "gender", "maritalStatus",
                        "dependentAmount", "employment", "accountNumber", "passport.passportUUID",
                        "passport.issueBranch", "passport.issueDate")
                .isEqualTo(expectedClient);
    }
}

