package com.kata.banking.controller;

import com.kata.banking.dto.AccountDTO;
import com.kata.banking.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(statements = {
        "DELETE FROM transaction WHERE account_id = 1",
        "DELETE FROM account WHERE id = 1",
        "INSERT INTO account (id, balance) VALUES (1, 100.0)"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class AccountControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AccountService accountService;

    @Test
    void depositEndpoint_shouldReturnOk() {
        // Given
        long accountId = 1L;
        double amount = 100.0;

        // When
        ResponseEntity<Void> response = restTemplate.postForEntity("/api/v1/accounts/" + accountId + "/deposit?amount=" + amount, null, Void.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void withdrawEndpoint_shouldReturnOk() {
        // Given
        long accountId = 1L;
        double amount = 50.0;

        // When
        ResponseEntity<Void> response = restTemplate.postForEntity("/api/v1/accounts/" + accountId + "/withdraw?amount=" + amount, null, Void.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getCurrentAccountState_shouldReturnAccount() {
        // Given
        long accountId = 1L;

        // When
        ResponseEntity<AccountDTO> response = restTemplate.getForEntity("/api/v1/accounts/" + accountId, AccountDTO.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void depositEndpoint_shouldReturnBadRequest_whenAmountIsNegative() {
        // Given
        long accountId = 1L;
        double amount = -100.0;

        // When
        ResponseEntity<Void> response = restTemplate.postForEntity("/api/v1/accounts/" + accountId + "/deposit?amount=" + amount, null, Void.class);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void withdrawEndpoint_shouldReturnBadRequest_whenInsufficientFunds() {
        // Given
        long accountId = 1L;
        double amount = 200.0;

        // When
        ResponseEntity<Void> response = restTemplate.postForEntity("/api/v1/accounts/" + accountId + "/withdraw?amount=" + amount, null, Void.class);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void getCurrentAccountState_shouldReturnNotFound_whenAccountDoesNotExist() {
        // Given
        long accountId = 999L;

        // When
        ResponseEntity<Void> response = restTemplate.getForEntity("/api/v1/accounts/" + accountId, Void.class);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
