package com.kata.banking.controller;

import com.kata.banking.dto.AccountDTO;
import com.kata.banking.service.AccountService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST pour la gestion des comptes.
 * Fournit des endpoints pour les opérations de dépôt, retrait et la récupération de l'état du compte.
 */
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Effectue un dépôt sur un compte spécifique.
     *
     * @param id     l'ID du compte sur lequel effectuer le dépôt
     * @param amount le montant à déposer (doit être positif)
     */
    @PostMapping("/{id}/deposit")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deposit(@PathVariable Long id, @RequestParam @NotNull Double amount) {
        accountService.deposit(id, amount);
    }

    /**
     * Effectue un retrait d'un compte spécifique.
     *
     * @param id     l'ID du compte sur lequel effectuer le retrait
     * @param amount le montant à retirer (doit être positif et inférieur ou égal au solde du compte)
     */
    @PostMapping("/{id}/withdraw")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void withdraw(@PathVariable Long id, @RequestParam @NotNull Double amount) {
        accountService.withdraw(id, amount);
    }

    /**
     * Récupère l'état actuel d'un compte.
     *
     * @param id l'ID du compte
     * @return l'état actuel du compte sous forme d'AccountDTO
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDTO getCurrentAccountState(@PathVariable Long id) {
        return accountService.getCurrentAccountState(id);
    }
}
