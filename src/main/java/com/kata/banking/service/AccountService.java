package com.kata.banking.service;

import com.kata.banking.dto.AccountDTO;

public interface AccountService {
    void deposit(Long accountId, Double amount);
    void withdraw(Long accountId, Double amount);
    AccountDTO getCurrentAccountState(Long accountId);
}
