package com.kata.banking.mapper;

import com.kata.banking.dto.TransactionDTO;
import com.kata.banking.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AccountMapper.class)
public interface TransactionMapper {

    TransactionDTO transactionToTransactionDTO(Transaction transaction);
    Transaction transactionDTOToTransaction(TransactionDTO transactionDTO);
}

