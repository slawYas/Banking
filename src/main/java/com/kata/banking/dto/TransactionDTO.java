package com.kata.banking.dto;

import com.kata.banking.enums.OperationType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class TransactionDTO {
    private Long id;
    private LocalDateTime date;
    private OperationType operation;
    private Double amount;
    private Double balanceAfterTransaction;
    private AccountDTO account;
}
