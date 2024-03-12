package com.v2.accountservice.service.implementation;


import com.v2.accountservice.dto.CreditDTO;
import com.v2.accountservice.dto.DebitDTO;
import com.v2.accountservice.dto.OperationDTO;
import com.v2.accountservice.entity.Account;
import com.v2.accountservice.entity.Operation;
import com.v2.accountservice.enums.AccountStatus;
import com.v2.accountservice.enums.OperationType;
import com.v2.accountservice.repository.AccountRepository;
import com.v2.accountservice.repository.OperationRepository;
import com.v2.accountservice.util.Mappers;
import com.v2.accountservice.exception.AccountNotActivatedException;
import com.v2.accountservice.exception.AccountNotFoundException;
import com.v2.accountservice.exception.BalanceNotSufficientException;
import com.v2.accountservice.exception.OperationNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class OperationServiceImplTest {

    @Mock
    private OperationRepository operationRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private Mappers mappers;

    @InjectMocks
    private OperationServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new OperationServiceImpl(operationRepository, accountRepository, mappers);
    }

    @Test
    void credit() throws AccountNotFoundException, AccountNotActivatedException, BalanceNotSufficientException {
        LocalDateTime dateTime = LocalDateTime.now();
        String accountId = "accountId";
        Account account = Account.builder().id(accountId).customerId("customerId").balance(BigDecimal.valueOf(5000)).currency(com.v2.accountservice.enums.Currency.EUR)
                .status(AccountStatus.ACTIVATED).creation(dateTime).lastUpdate(dateTime).operations(new ArrayList<>())
                .build();
        Account accountUpdated = Account.builder().id(accountId).customerId("customerId").balance(BigDecimal.valueOf(10000)).currency(com.v2.accountservice.enums.Currency.EUR)
                .status(AccountStatus.ACTIVATED).creation(dateTime).lastUpdate(dateTime).operations(new ArrayList<>())
                .build();

        CreditDTO creditDTO = new CreditDTO(accountId, "description", BigDecimal.valueOf(5000));
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(any())).thenReturn(accountUpdated);
        when(operationRepository.save(any())).thenReturn(
                Operation.builder().id("operationId")
                        .date(LocalDateTime.now()).type(OperationType.DEBIT).amount(BigDecimal.valueOf(5000)).currency(com.v2.accountservice.enums.Currency.EUR).description("description").account(accountUpdated)
                        .build()
        );
        CreditDTO result = service.credit(creditDTO);
        assertNotNull(result);
        assertEquals(result.accountId(), accountId);
        assertEquals(result.description(), creditDTO.description());
        assertEquals(result.amount(), creditDTO.amount());
        verify(accountRepository, times(1)).save(any());
        verify(operationRepository, times(1)).save(any());
    }

    @Test
    void debit() throws AccountNotActivatedException, AccountNotFoundException, BalanceNotSufficientException {
        LocalDateTime dateTime = LocalDateTime.now();
        String accountId = "accountId";
        Account account = Account.builder().id(accountId).customerId("customerId").balance(BigDecimal.valueOf(10000)).currency(com.v2.accountservice.enums.Currency.EUR)
                .status(AccountStatus.ACTIVATED).creation(dateTime).lastUpdate(dateTime).operations(new ArrayList<>())
                .build();
        Account accountUpdated = Account.builder().id(accountId).customerId("customerId").balance(BigDecimal.valueOf(5000)).currency(com.v2.accountservice.enums.Currency.EUR)
                .status(AccountStatus.ACTIVATED).creation(dateTime).lastUpdate(dateTime).operations(new ArrayList<>())
                .build();

        DebitDTO debitDTO = new DebitDTO(accountId, "description", BigDecimal.valueOf(5000));
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(any())).thenReturn(accountUpdated);
        when(operationRepository.save(any())).thenReturn(
                Operation.builder().id("operationId")
                        .date(LocalDateTime.now()).type(OperationType.CREDIT).amount(BigDecimal.valueOf(5000))
                        .currency(com.v2.accountservice.enums.Currency.EUR).description("description").account(accountUpdated)
                        .build()
        );
        DebitDTO result = service.debit(debitDTO);
        assertNotNull(result);
        assertEquals(result.accountId(), accountId);
        assertEquals(result.description(), debitDTO.description());
        assertEquals(result.amount(), debitDTO.amount());
        verify(accountRepository, times(1)).save(any());
        verify(operationRepository, times(1)).save(any());
    }

    @Test
    void getById() throws OperationNotFoundException {
        String id = "operationId";
        Operation operation = Operation.builder().id(id)
                .date(LocalDateTime.now()).type(OperationType.CREDIT).amount(BigDecimal.valueOf(5000))
                .currency(com.v2.accountservice.enums.Currency.EUR).description("description")
                .build();
        when(operationRepository.findById(id)).thenReturn(Optional.of(operation));
        when(mappers.fromOperation(operation)).thenReturn(
                new OperationDTO(id, operation.getDate(), operation.getType(), operation.getAmount(), operation.getCurrency(), operation.getDescription(), "accountId")
        );
        OperationDTO response = service.getById(id);
        assertEquals(response.id(), operation.getId());
    }

}