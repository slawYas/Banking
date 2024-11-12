package com.kata.banking.service.impl;

import com.kata.banking.dto.AccountDTO;
import com.kata.banking.enums.OperationType;
import com.kata.banking.exception.AccountNotFoundException;
import com.kata.banking.exception.InsufficientFundsException;
import com.kata.banking.exception.InvalidAmountException;
import com.kata.banking.helper.TransactionHelper;
import com.kata.banking.mapper.AccountMapper;
import com.kata.banking.model.Account;
import com.kata.banking.repository.AccountRepository;
import com.kata.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final TransactionHelper transactionHelper;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper, TransactionHelper transactionHelper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.transactionHelper = transactionHelper;
    }

    @Transactional
    @Override
    public void deposit(Long accountId, Double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException("Le montant doit être positif");
        }

        Account account = getAccountById(accountId);
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        // Enregistrer la transaction
        transactionHelper.record(account, OperationType.DEPOSIT, amount, account.getBalance());
    }

    @Transactional
    @Override
    public void withdraw(Long accountId, Double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException("Le montant doit être positif");
        }

        Account account = getAccountById(accountId);

        if (amount > account.getBalance()) {
            throw new InsufficientFundsException("Fonds insuffisants");
        }

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);

        // Enregistrer la transaction
        transactionHelper.record(account, OperationType.WITHDRAWAL, amount, account.getBalance());
    }

    @Override
    public AccountDTO getCurrentAccountState(Long accountId) {
        Account account = getAccountById(accountId);
        return accountMapper.accountToAccountDTO(account);
    }

    @Override
    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
    }
}

