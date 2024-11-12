package com.kata.banking.model;

import com.kata.banking.enums.OperationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private OperationType operation;

    private Double amount;
    private Double balanceAfterTransaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    public Transaction(LocalDateTime date, OperationType operation, Double amount, Double balanceAfterTransaction, Account account) {
        this.date = date;
        this.operation = operation;
        this.amount = amount;
        this.balanceAfterTransaction = balanceAfterTransaction;
        this.account = account;
    }
}
