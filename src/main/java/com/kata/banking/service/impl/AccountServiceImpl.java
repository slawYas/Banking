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

/**
 * Implémentation de AccountService pour la gestion des comptes.
 * Gère le dépôt, le retrait et la récupération de l'état du compte.
 */
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

    /**
     * Effectue un dépôt sur un compte spécifique.
     *
     * @param accountId l'ID du compte sur lequel effectuer le dépôt
     * @param amount    le montant à déposer
     */
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

    /**
     * Effectue un retrait d'un compte spécifique.
     *
     * @param accountId l'ID du compte sur lequel effectuer le retrait
     * @param amount    le montant à retirer
     */
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

    /**
     * Récupère l'état actuel d'un compte.
     *
     * @param accountId l'ID du compte
     * @return l'état actuel du compte sous forme d'AccountDTO
     */
    @Override
    public AccountDTO getCurrentAccountState(Long accountId) {
        Account account = getAccountById(accountId);
        return accountMapper.accountToAccountDTO(account);
    }

    /**
     * Récupère un compte par son ID.
     *
     * @param accountId l'ID du compte
     * @return le compte
     */
    private Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
    }
}

