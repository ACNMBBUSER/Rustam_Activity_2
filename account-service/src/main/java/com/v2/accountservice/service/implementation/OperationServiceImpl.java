package com.v2.accountservice.service.implementation;

import com.v2.accountservice.dto.HistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.v2.accountservice.dto.CreditDTO;
import com.v2.accountservice.dto.DebitDTO;
import com.v2.accountservice.dto.OperationDTO;
import com.v2.accountservice.entity.Account;
import com.v2.accountservice.entity.Operation;
import com.v2.accountservice.enums.AccountStatus;
import com.v2.accountservice.enums.OperationType;
import com.v2.accountservice.exception.AccountNotActivatedException;
import com.v2.accountservice.exception.AccountNotFoundException;
import com.v2.accountservice.exception.BalanceNotSufficientException;
import com.v2.accountservice.exception.OperationNotFoundException;
import com.v2.accountservice.repository.AccountRepository;
import com.v2.accountservice.repository.OperationRepository;
import com.v2.accountservice.service.OperationService;
import com.v2.accountservice.util.Mappers;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;
    private final AccountRepository accountRepository;
    private final Mappers mappers;

    public OperationServiceImpl(OperationRepository operationRepository, AccountRepository accountRepository, Mappers mappers) {
        this.operationRepository = operationRepository;
        this.accountRepository = accountRepository;
        this.mappers = mappers;
    }

    /**
     * Credit funds to an account.
     *
     */
    @Transactional
    @Override
    public CreditDTO credit(@NotNull CreditDTO creditDTO) throws AccountNotFoundException, BalanceNotSufficientException, AccountNotActivatedException {
        log.info("In credit() :");
        Account account = accountRepository.findById(creditDTO.accountId())
                .orElseThrow( () -> new AccountNotFoundException("Account you try to credit not found. id '"+creditDTO.accountId()+"'."));
        if(account.getStatus().equals(AccountStatus.BLOCKED)){
            throw new AccountNotActivatedException("Account you try to credit is not activated");
        }
        if (creditDTO.amount() == null || creditDTO.amount().compareTo(BigDecimal.ZERO) < 0){
            throw new BalanceNotSufficientException("Balance Not Sufficient : amount must be non-null and greater than zero");
        }
        account.setBalance(account.getBalance().add(creditDTO.amount()));
        account.setLastUpdate(LocalDateTime.now());
        Account accountUpdated = accountRepository.save(account);

        Operation operation = Operation.builder().id(UUID.randomUUID().toString()).amount(creditDTO.amount())
                .currency(accountUpdated.getCurrency()).description(creditDTO.description()).type(OperationType.CREDIT)
                .account(accountUpdated).date(accountUpdated.getLastUpdate())
                .build();
        Operation operationSaved = operationRepository.save(operation);
        log.info("account credited successfully");
        return new CreditDTO(operationSaved.getAccount().getId(), operationSaved.getDescription(), operationSaved.getAmount());
    }

    /**
     * Debit funds from an account.
     *
     */
    @Transactional
    @Override
    public DebitDTO debit(@NotNull DebitDTO debitDTO) throws AccountNotFoundException, BalanceNotSufficientException, AccountNotActivatedException {
        log.info("In debit() :");
        Account account = accountRepository.findById(debitDTO.accountId())
                .orElseThrow( () -> new AccountNotFoundException("Account you try to debit not found. id '"+debitDTO.accountId()+"'."));
        if(account.getStatus().equals(AccountStatus.BLOCKED)){
            throw new AccountNotActivatedException("Account you try to debit is not activated");
        }
        if (debitDTO.amount() == null || debitDTO.amount().compareTo(account.getBalance()) > 0){
            throw new BalanceNotSufficientException("Balance Not Sufficient : amount must be non-null and greater than account balance");
        }
        account.setBalance( account.getBalance().subtract(debitDTO.amount()));
        account.setLastUpdate(LocalDateTime.now());
        Account accountUpdated = accountRepository.save(account);

        Operation operation = Operation.builder()
                .id(UUID.randomUUID().toString()).amount(debitDTO.amount())
                .currency(accountUpdated.getCurrency()).description(debitDTO.description())
                .type(OperationType.DEBIT).account(accountUpdated).date(accountUpdated.getLastUpdate())
                .build();
        Operation operationSaved = operationRepository.save(operation);
        log.info("account debited successfully");
        return new DebitDTO(operationSaved.getAccount().getId(), operationSaved.getDescription(), operationSaved.getAmount());
    }

    /**
     * Retrieve an operation by its ID.
     *
     */
    @Override
    public OperationDTO getById(String id) throws OperationNotFoundException {
        log.info("In getById() :");
        Operation operation = operationRepository.findById(id)
                .orElseThrow( ()  -> new OperationNotFoundException("operation with id '"+id+"' not found"));
        log.info("operation found");
        return mappers.fromOperation(operation);
    }

    @Override
    public HistoryDTO getHistory(String accountId, int page, int size) throws AccountNotFoundException {
        log.info("In getHistory() :");
        Account account = accountRepository.findById(accountId)
                .orElseThrow( () -> new AccountNotFoundException("account with id '"+accountId+"'not found"));

        Page<Operation> operationPage = operationRepository.findByAccountIdOrderByDateDesc(account.getId(), PageRequest.of(page, size));
        List<OperationDTO> operationDTOList = operationPage.getContent()
                .stream()
                .map(mappers::fromOperation)
                .toList();

        log.info("history found");
        return new HistoryDTO(account.getCustomerId(), account.getId(),
                account.getBalance(), page, operationPage.getTotalPages(), size, operationDTOList
        );
    }

}
