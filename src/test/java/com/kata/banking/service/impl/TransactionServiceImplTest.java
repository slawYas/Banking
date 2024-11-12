package com.kata.banking.service.impl;

import com.kata.banking.dto.TransactionDTO;
import com.kata.banking.mapper.TransactionMapper;
import com.kata.banking.model.Transaction;
import com.kata.banking.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyLong;
import static org.mockito.MockitoAnnotations.*;

@ActiveProfiles("test")
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void getTransactionHistory_shouldReturnTransactions() {
        // Given
        Long accountId = 1L;
        Transaction transaction = new Transaction();
        List<Transaction> transactions = List.of(transaction);
        when(transactionRepository.findByAccountId(accountId)).thenReturn(transactions);

        TransactionDTO transactionDTO = new TransactionDTO();
        when(transactionMapper.transactionToTransactionDTO(transaction)).thenReturn(transactionDTO);

        // When
        List<TransactionDTO> result = transactionService.getTransactionHistory(accountId);

        // Then
        assertEquals(1, result.size());
        assertEquals(transactionDTO, result.get(0));
    }
}