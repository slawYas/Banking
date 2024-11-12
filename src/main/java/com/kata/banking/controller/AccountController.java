package com.kata.banking.controller;

import com.kata.banking.dto.AccountDTO;
import com.kata.banking.service.AccountService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{id}/deposit")
    @ResponseStatus(HttpStatus.OK)
    public void deposit(@PathVariable Long id, @RequestParam @NotNull Double amount) {
        accountService.deposit(id, amount);
    }

    @PostMapping("/{id}/withdraw")
    @ResponseStatus(HttpStatus.OK)
    public void withdraw(@PathVariable Long id, @RequestParam @NotNull Double amount) {
        accountService.withdraw(id, amount);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDTO getCurrentAccountState(@PathVariable Long id) {
        return accountService.getCurrentAccountState(id);
    }
}
