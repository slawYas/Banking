package com.kata.banking.helper;

import com.kata.banking.enums.OperationType;
import com.kata.banking.model.Account;
import com.kata.banking.model.Transaction;
import com.kata.banking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransactionHelper {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionHelper(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * Enregistre une transaction pour un compte spécifique.
     * @param account le compte associé à la transaction.
     * @param operation l'opération (DEPOSIT, WITHDRAWAL, etc.)
     * @param amount le montant de la transaction.
     * @param balanceAfterTransaction le solde après la transaction.
     */
    public void record(Account account, OperationType operation, Double amount, Double balanceAfterTransaction) {
        Transaction transaction = new Transaction(LocalDateTime.now(), operation, amount, balanceAfterTransaction, account);
        transactionRepository.save(transaction);
    }
}
