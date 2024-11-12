package com.kata.banking.service.impl;

import com.kata.banking.dto.TransactionDTO;
import com.kata.banking.exception.TransactionNotFoundException;
import com.kata.banking.mapper.TransactionMapper;
import com.kata.banking.model.Transaction;
import com.kata.banking.repository.TransactionRepository;
import com.kata.banking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implémentation de TransactionService pour la gestion des transactions.
 * Fournit la fonctionnalité de récupérer l'historique des transactions d'un compte.
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    /**
     * Récupère l'historique des transactions pour un compte spécifique.
     *
     * @param accountId l'ID du compte
     * @return une liste des transactions du compte sous forme d'objets TransactionDTO
     */
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
