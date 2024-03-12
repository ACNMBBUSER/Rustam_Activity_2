package com.v2.transactionservice.service.implementation;

import com.v2.transactionservice.controller.AccountRestController;
import com.v2.transactionservice.dto.AccountDTO;
import com.v2.transactionservice.dto.CreditDTO;
import com.v2.transactionservice.dto.DebitDTO;
import com.v2.transactionservice.dto.TransactionDTO;
import com.v2.transactionservice.entity.Transaction;
import com.v2.transactionservice.enums.AccountType;
import com.v2.transactionservice.enums.TransactionType;
import com.v2.transactionservice.exception.AccountNotFoundException;
import com.v2.transactionservice.exception.TransactionNotFoundException;
import com.v2.transactionservice.repository.TransactionRepository;
import com.v2.transactionservice.service.TransactionService;
import com.v2.transactionservice.utils.Mappers;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final Mappers mappers;

    private final AccountRestController accountRestClient;

    public TransactionServiceImpl(TransactionRepository transactionRepository, Mappers mappers, AccountRestController accountRestClient) {
        this.transactionRepository = transactionRepository;
        this.mappers = mappers;
        this.accountRestClient = accountRestClient;
    }

    @Transactional
    @Override
    public CreditDTO credit(@NotNull CreditDTO creditDTO) throws AccountNotFoundException {
        log.info("In credit() :");
//        AccountDTO account = getByAccountId(creditDTO.id());
//        if (account == null) {
//            throw new AccountNotFoundException("Account with id '" + creditDTO.id() + " not found");
//        }
        Transaction transaction = Transaction.builder()
                .id(UUID.randomUUID().toString()).accountId(creditDTO.id()).transactionDate(LocalDateTime.now())
                .description(creditDTO.description()).accountType(AccountType.SAVING).amount(creditDTO.amount())
                .type(TransactionType.CREDIT).balance(creditDTO.amount()).build();

        Transaction transactionSaved = transactionRepository.save(transaction);
        log.info("credit successfully");
        return new CreditDTO(transactionSaved.getId(), transactionSaved.getDescription(), transactionSaved.getAmount(), transactionSaved.getAccountType());
    }

    @Transactional
    @Override
    public DebitDTO debit(@NotNull DebitDTO debitDTO) throws AccountNotFoundException{
        log.info("In credit() :");
//        AccountDTO account = getByAccountId(debitDTO.id());
//        if (account == null) {
//            throw new AccountNotFoundException("Customer with id '" + debitDTO.id() + "not found");
//        }
        Transaction transaction = Transaction.builder()
                .id(UUID.randomUUID().toString()).accountId(debitDTO.id()).transactionDate(LocalDateTime.now())
                .description(debitDTO.description()).accountType(AccountType.SAVING).amount(debitDTO.amount())
                .type(TransactionType.DEBIT).build();

        Transaction transactionSaved = transactionRepository.save(transaction);
        log.info("credit successfully");
        return new DebitDTO(transactionSaved.getId(), transactionSaved.getDescription(), transactionSaved.getAmount(), transactionSaved.getAccountType());
    }

    public @Nullable AccountDTO getByAccountId(String id) {
        log.info("In getAccountById()");
        try{
            AccountDTO account = accountRestClient.getById(id);
            log.info("account found");
            return account;
        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public TransactionDTO getById(String id) throws TransactionNotFoundException {
        log.info("In getById() :");
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow( ()  -> new TransactionNotFoundException("operation with id '"+id+"' not found"));
        log.info("operation found");
        return mappers.fromTransaction(transaction);
    }

}
