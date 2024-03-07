package com.v2.accountservice.service.implementation;

import com.v2.accountservice.controller.CustomerRestClient;
import com.v2.accountservice.dto.AccountDTO;
import com.v2.accountservice.dto.CustomerDTO;
import com.v2.accountservice.entity.Account;
import com.v2.accountservice.enums.AccountStatus;
import com.v2.accountservice.enums.Currency;
import com.v2.accountservice.enums.Sex;
import com.v2.accountservice.exception.AccountNotFoundException;
import com.v2.accountservice.exception.CustomerAlreadyHaveAccountException;
import com.v2.accountservice.exception.CustomerNotFoundException;
import com.v2.accountservice.repository.AccountRepository;
import com.v2.accountservice.util.IdGenerator;
import com.v2.accountservice.util.Mappers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private IdGenerator idGenerator;

    @Mock
    private Mappers mappers;

    @Mock
    CustomerRestClient customerRestClient;

    @InjectMocks
    private AccountServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new AccountServiceImpl(accountRepository, mappers, customerRestClient, idGenerator);
    }

    @Test
    void getById() throws AccountNotFoundException {
        LocalDateTime dateTime = LocalDateTime.now();
        String id = "id";
        Account account = Account.builder()
                .id(id)
                .customerId("customerId").balance(BigDecimal.valueOf(5000)).currency(Currency.EUR)
                .status(AccountStatus.ACTIVATED).creation(dateTime).lastUpdate(dateTime)
                .operations(new ArrayList<>())
                .build();
        when(accountRepository.findById(id)).thenReturn(Optional.of(account));
        when(mappers.fromAccount(any())).thenReturn(
                new AccountDTO(id, "customerId", BigDecimal.valueOf(5000), Currency.EUR,
                        AccountStatus.ACTIVATED, dateTime, dateTime
                )
        );
        AccountDTO accountDTO = service.getById(id);
        assertNotNull(accountDTO);
        assertEquals(accountDTO.id(), account.getId());
        assertEquals(accountDTO.customerId(), account.getCustomerId());
    }

    @Test
    void getByCustomerId() throws AccountNotFoundException {
        LocalDateTime dateTime = LocalDateTime.now();
        String id = "id";
        String customerId = "customerId";
        Account account = Account.builder()
                .id(id)
                .customerId(customerId).balance(BigDecimal.valueOf(5000)).currency(Currency.EUR)
                .status(AccountStatus.ACTIVATED).creation(dateTime).lastUpdate(dateTime)
                .operations(new ArrayList<>())
                .build();
        when(accountRepository.findByCustomerId(customerId)).thenReturn(account);
        when(mappers.fromAccount(any())).thenReturn(
                new AccountDTO(id, customerId, BigDecimal.valueOf(5000), Currency.EUR,
                        AccountStatus.ACTIVATED, dateTime, dateTime
                )
        );
        AccountDTO accountDTO = service.getByCustomerId(customerId);
        assertNotNull(accountDTO);
        assertEquals(accountDTO.id(), account.getId());
        assertEquals(accountDTO.customerId(), account.getCustomerId());
    }

    @Test
    void save() throws CustomerNotFoundException, CustomerAlreadyHaveAccountException {
        String id = "id";
        String customerId = "customerId";
        AccountDTO accountDTO = new AccountDTO(
                id, "customerId", BigDecimal.valueOf(5000), Currency.EUR,
                AccountStatus.ACTIVATED, LocalDateTime.now(), LocalDateTime.now()
        );
        Account account = Account.builder()
                .id(id).customerId("customerId").balance(BigDecimal.valueOf(5000))
                .currency(Currency.EUR).status(AccountStatus.ACTIVATED).creation(LocalDateTime.now())
                .lastUpdate(LocalDateTime.now()).operations(new ArrayList<>())
                .build();
        when(customerRestClient.getById(customerId)).thenReturn(
                new CustomerDTO(customerId, "John", "Doe", "GABON", new Date(), "Nationality",
                        Sex.M, "cin", "email", "phone", LocalDateTime.now(), null)
        );
        when(mappers.fromAccountDTO(accountDTO)).thenReturn(account);
        when(accountRepository.checkIfCustomerIdExists(anyString())).thenReturn(false);
        when(idGenerator.autoGenerate()).thenReturn(UUID.randomUUID().toString());
        service.save(accountDTO);
        verify(accountRepository, times(1)).save(any());
    }

    @Test
    void deleteById() {
        String accountId = "accountId";
        service.deleteById(accountId);
        verify(accountRepository, times(1)).deleteById(accountId);
    }

    @Test
    void updateStatus() throws AccountNotFoundException {
        String id = "accountId";
        AccountDTO accountDTO = new AccountDTO(
                id, "customerId", BigDecimal.valueOf(5000), Currency.EUR,
                AccountStatus.BLOCKED, LocalDateTime.now(), LocalDateTime.now()
        );
        Account account = Account.builder()
                .id(id).customerId("customerId").balance(BigDecimal.valueOf(5000))
                .currency(Currency.EUR).status(AccountStatus.ACTIVATED).creation(LocalDateTime.now())
                .lastUpdate(LocalDateTime.now()).operations(new ArrayList<>())
                .build();
        when(accountRepository.findById(id)).thenReturn(Optional.of(account));
        when(mappers.fromAccount(any())).thenReturn(accountDTO);
        AccountDTO response = service.updateStatus(new AccountDTO(
                id, "customerId", BigDecimal.valueOf(5000), Currency.EUR,
                AccountStatus.BLOCKED, LocalDateTime.now(), LocalDateTime.now()
        ));
        verify(accountRepository, times(1)).save(any());
        assertNotNull(response);
        assertEquals(response.id(), id);
        assertNotEquals(AccountStatus.ACTIVATED, response.status());
    }
}