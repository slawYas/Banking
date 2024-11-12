package com.kata.banking.service.impl;

import com.kata.banking.enums.OperationType;
import com.kata.banking.exception.AccountNotFoundException;
import com.kata.banking.exception.InsufficientFundsException;
import com.kata.banking.exception.InvalidAmountException;
import com.kata.banking.helper.TransactionHelper;
import com.kata.banking.model.Account;
import com.kata.banking.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

@ActiveProfiles("test")
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionHelper transactionHelper;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void deposit_shouldUpdateBalanceAndRecordTransaction() {
        // Given
        Long accountId = 1L;
        Double amount = 100.0;
        Account account = new Account();
        account.setId(accountId);
        account.setBalance(0.0);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        // When
        accountService.deposit(accountId, amount);

        // Then
        verify(accountRepository, times(1)).save(account);
        verify(transactionHelper, times(1)).record(account, OperationType.DEPOSIT, amount, account.getBalance());
    }

    @Test
    void withdraw_shouldUpdateBalanceAndRecordTransaction() {
        // Given
        Long accountId = 1L;
        Double amount = 50.0;
        Account account = new Account();
        account.setId(accountId);
        account.setBalance(100.0);

        // Configurer le mock pour retourner un compte existant
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        // When
        accountService.withdraw(accountId, amount);

        // Then
        verify(accountRepository, times(1)).save(account);
        verify(transactionHelper, times(1)).record(account, OperationType.WITHDRAWAL, amount, account.getBalance());
    }

    @Test
    void deposit_shouldThrowException_whenAmountIsNegative() {
        // Given
        Long accountId = 1L;
        Double amount = -100.0;
        Account account = new Account();
        account.setId(accountId);
        account.setBalance(0.0);

        // Configurer le mock pour retourner un compte existant
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        // When & Then
        assertThrows(InvalidAmountException.class, () -> accountService.deposit(accountId, amount));
    }

    @Test
    void withdraw_shouldThrowException_whenAmountIsNegative() {
        // Given
        Long accountId = 1L;
        Double amount = -50.0;
        Account account = new Account();
        account.setId(accountId);
        account.setBalance(0.0);

        // Configurer le mock pour retourner un compte existant
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        // When & Then
        assertThrows(InvalidAmountException.class, () -> accountService.withdraw(accountId, amount));
    }

    @Test
    void withdraw_shouldThrowException_whenInsufficientFunds() {
        // Given
        Long accountId = 1L;
        Double amount = 150.0;
        Account account = new Account();
        account.setId(accountId);
        account.setBalance(100.0);

        // Configurer le mock pour retourner un compte existant
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        // When & Then
        assertThrows(InsufficientFundsException.class, () -> accountService.withdraw(accountId, amount));
    }

    @Test
    void deposit_shouldThrowException_whenAccountNotFound() {
        // Given
        Long accountId = 1L;
        Double amount = 100.0;
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(AccountNotFoundException.class, () -> accountService.deposit(accountId, amount));
    }

    @Test
    void withdraw_shouldThrowException_whenAccountNotFound() {
        // Given
        Long accountId = 1L;
        Double amount = 50.0;
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(AccountNotFoundException.class, () -> accountService.withdraw(accountId, amount));
    }
}
