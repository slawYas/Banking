package com.kata.banking.mapper;

import com.kata.banking.dto.AccountDTO;
import com.kata.banking.dto.TransactionDTO;
import com.kata.banking.enums.OperationType;
import com.kata.banking.model.Account;
import com.kata.banking.model.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class TransactionMapperTest {

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private TransactionMapperImpl transactionMapper;

    @Test
    void shouldMapTransactionToTransactionDTO() {
        // Given
        Account account = new Account();
        account.setId(1L);
        account.setBalance(500.0);

        Transaction transaction = new Transaction(
                LocalDateTime.now(),
                OperationType.DEPOSIT,
                200.0,
                700.0,
                account
        );
        transaction.setId(1L);

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setBalance(account.getBalance());

        // Configure AccountMapper mock pour renvoyer accountDTO quand accountToAccountDTO est appelé
        when(accountMapper.accountToAccountDTO(account)).thenReturn(accountDTO);

        // When
        TransactionDTO transactionDTO = transactionMapper.transactionToTransactionDTO(transaction);

        // Then
        assertNotNull(transactionDTO);
        assertEquals(transaction.getId(), transactionDTO.getId());
        assertEquals(transaction.getDate(), transactionDTO.getDate());
        assertEquals(transaction.getOperation(), transactionDTO.getOperation());
        assertEquals(transaction.getAmount(), transactionDTO.getAmount());
        assertEquals(transaction.getBalanceAfterTransaction(), transactionDTO.getBalanceAfterTransaction());

        assertNotNull(transactionDTO.getAccount());
        assertEquals(accountDTO.getId(), transactionDTO.getAccount().getId());
        assertEquals(accountDTO.getBalance(), transactionDTO.getAccount().getBalance());
    }


    @Test
    void shouldMapTransactionDTOToTransaction() {
        // Given
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1L);
        accountDTO.setBalance(500.0);

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(100L);
        transactionDTO.setDate(LocalDateTime.now());
        transactionDTO.setOperation(OperationType.WITHDRAWAL);
        transactionDTO.setAmount(150.0);
        transactionDTO.setBalanceAfterTransaction(350.0);
        transactionDTO.setAccount(accountDTO);

        // Préparez un Account correspondant au AccountDTO pour le mapper
        Account account = new Account();
        account.setId(accountDTO.getId());
        account.setBalance(accountDTO.getBalance());

        // Configure AccountMapper mock pour renvoyer un Account quand accountDTOToAccount est appelé
        when(accountMapper.accountDTOToAccount(accountDTO)).thenReturn(account);

        // When
        Transaction transaction = transactionMapper.transactionDTOToTransaction(transactionDTO);

        // Then
        assertNotNull(transaction);
        assertEquals(transactionDTO.getDate(), transaction.getDate());
        assertEquals(transactionDTO.getOperation(), transaction.getOperation());
        assertEquals(transactionDTO.getAmount(), transaction.getAmount());
        assertEquals(transactionDTO.getBalanceAfterTransaction(), transaction.getBalanceAfterTransaction());

        assertNotNull(transaction.getAccount());
        assertEquals(accountDTO.getBalance(), transaction.getAccount().getBalance());
    }
}
