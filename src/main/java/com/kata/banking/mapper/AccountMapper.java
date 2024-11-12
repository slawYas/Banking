package com.kata.banking.mapper;

import com.kata.banking.dto.AccountDTO;
import com.kata.banking.model.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDTO accountToAccountDTO(Account account);
    Account accountDTOToAccount(AccountDTO accountDTO);
}

