package com.kata.banking.controller;

import com.kata.banking.dto.TransactionDTO;
import com.kata.banking.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(statements = {
        "DELETE FROM transaction WHERE account_id = 1",
        "DELETE FROM account WHERE id = 1",
        "INSERT INTO account (id, balance) VALUES (1, 100.0)",
        "INSERT INTO transaction (id, account_id, date, operation, amount, balance_after_transaction) VALUES (1, 1, NOW(), 'DEPOSIT', 50.0, 150.0)"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class TransactionControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TransactionService transactionService;

    @Test
    void getTransactionHistory_shouldReturnTransactions() {
        // Given
        long accountId = 1L;

        // When
        ResponseEntity<TransactionDTO[]> response = restTemplate.getForEntity("/api/v1/transactions/account/" + accountId + "/history", TransactionDTO[].class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getTransactionHistory_shouldReturnNotFound_whenAccountDoesNotExist() {
        // Given
        long accountId = 999L;

        // When
        ResponseEntity<Void> response = restTemplate.getForEntity("/api/v1/transactions/account/" + accountId + "/history", Void.class);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
