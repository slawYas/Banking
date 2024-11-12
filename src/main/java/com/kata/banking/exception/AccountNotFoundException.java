package com.kata.banking.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(Long accountId) {
        super("Compte non trouvée avec l'id " + accountId);
    }
}
