package com.kata.banking.service;

import com.kata.banking.dto.TransactionDTO;

import java.util.List;

public interface TransactionService {
    List<TransactionDTO> getTransactionHistory(Long accountId);
}

