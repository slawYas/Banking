package com.kata.banking.exception;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(Long accountId) {
        super("Aucune transaction n'est trouvée avec le compte " + accountId);
    }
}
