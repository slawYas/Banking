package com.kata.banking.controller;

import com.kata.banking.dto.TransactionDTO;
import com.kata.banking.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/account/{accountId}/history")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionDTO> getTransactionHistory(@PathVariable Long accountId) {
        return transactionService.getTransactionHistory(accountId);
    }
}
