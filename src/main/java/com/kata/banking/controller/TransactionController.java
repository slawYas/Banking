package com.kata.banking.controller;

import com.kata.banking.dto.TransactionDTO;
import com.kata.banking.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des transactions.
 * Fournit un endpoint pour récupérer l'historique des transactions d'un compte.
 */
@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Récupère l'historique des transactions pour un compte spécifique.
     *
     * @param accountId l'ID du compte
     * @return une liste des transactions du compte sous forme d'objets TransactionDTO
     */
    @GetMapping("/account/{accountId}/history")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionDTO> getTransactionHistory(@PathVariable Long accountId) {
        return transactionService.getTransactionHistory(accountId);
    }
}
