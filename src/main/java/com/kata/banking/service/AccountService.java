package com.kata.banking.service;

import com.kata.banking.dto.AccountDTO;
import com.kata.banking.model.Account;

public interface AccountService {
    void deposit(Long accountId, Double amount);
    void withdraw(Long accountId, Double amount);
    AccountDTO getCurrentAccountState(Long accountId);
    Account getAccountById(Long accountId);
}
