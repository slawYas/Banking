package com.kata.banking.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.kata.banking.dto.AccountDTO;
import com.kata.banking.model.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class AccountMapperTest {

    private AccountMapper accountMapper;

    @BeforeEach
    void setUp() {
        accountMapper = Mappers.getMapper(AccountMapper.class);
    }

    @Test
    void shouldMapAccountToAccountDTO() {
        // Given
        Account account = new Account();
        account.setId(1L);
        account.setBalance(100.0);

        // When
        AccountDTO accountDTO = accountMapper.accountToAccountDTO(account);

        // Then
        assertThat(accountDTO).isNotNull();
        assertThat(accountDTO.getId()).isEqualTo(account.getId());
        assertThat(accountDTO.getBalance()).isEqualTo(account.getBalance());
    }

    @Test
    void shouldMapAccountDTOToAccount() {
        // Given
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setBalance(200.0);

        // When
        Account account = accountMapper.accountDTOToAccount(accountDTO);

        // Then
        assertNotNull(account);
        assertEquals(accountDTO.getBalance(), account.getBalance());
    }

    @Test
    void shouldReturnNullAccountDTOWhenAccountIsNull() {
        // Given && When
        AccountDTO accountDTO = accountMapper.accountToAccountDTO(null);

        // Then
        assertNull(accountDTO);
    }

    @Test
    void shouldReturnNullAccountWhenAccountDTOIsNull() {
        // Given && When
        Account account = accountMapper.accountDTOToAccount(null);

        // Then
        assertNull(account);
    }
}