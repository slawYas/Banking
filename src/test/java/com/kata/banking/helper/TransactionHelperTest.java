package com.kata.banking.helper;

import com.kata.banking.enums.OperationType;
import com.kata.banking.model.Account;
import com.kata.banking.model.Transaction;
import com.kata.banking.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.*;

@ActiveProfiles("test")
class TransactionHelperTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionHelper transactionHelper;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void record_shouldSaveTransaction() {
        // Given
        Account account = new Account();
        account.setId(1L);
        OperationType operation = OperationType.DEPOSIT;
        Double amount = 100.0;
        Double balanceAfterTransaction = 100.0;

        // When
        transactionHelper.record(account, operation, amount, balanceAfterTransaction);

        // Then
        ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionRepository).save(transactionCaptor.capture());
        Transaction savedTransaction = transactionCaptor.getValue();

        // Vérifications des attributs du Transaction capturé
        assertEquals(operation, savedTransaction.getOperation());
        assertEquals(amount, savedTransaction.getAmount());
        assertEquals(balanceAfterTransaction, savedTransaction.getBalanceAfterTransaction());
        assertEquals(account, savedTransaction.getAccount());
    }
}
