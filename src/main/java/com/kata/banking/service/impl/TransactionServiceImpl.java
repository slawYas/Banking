package com.kata.banking.service.impl;

import com.kata.banking.dto.TransactionDTO;
import com.kata.banking.exception.AccountNotFoundException;
import com.kata.banking.exception.TransactionNotFoundException;
import com.kata.banking.mapper.TransactionMapper;
import com.kata.banking.model.Account;
import com.kata.banking.model.Transaction;
import com.kata.banking.repository.TransactionRepository;
import com.kata.banking.service.AccountService;
import com.kata.banking.service.TransactionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public List<TransactionDTO> getTransactionHistory(Long accountId) {
        List<Transaction> transactions = transactionRepository.findByAccountId(accountId);

        if (transactions == null || transactions.isEmpty()) {
            throw new TransactionNotFoundException(accountId);
        }

        return transactions.stream()
                .map(transactionMapper::transactionToTransactionDTO)
                .toList();
    }
}
