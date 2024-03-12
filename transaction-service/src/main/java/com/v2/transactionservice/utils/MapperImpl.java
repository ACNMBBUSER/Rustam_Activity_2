package com.v2.transactionservice.utils;

import com.v2.transactionservice.dto.TransactionDTO;
import com.v2.transactionservice.entity.Transaction;
import com.v2.transactionservice.enums.AccountType;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class MapperImpl implements Mappers{
    @Override
    public Transaction fromTransactionDTO(@NotNull TransactionDTO dto) {
        return Transaction.builder().id(dto.id()).accountId(dto.accountId())
                .amount(dto.amount()).description(dto.description())
                .transactionDate(dto.date()).type(dto.type()).accountType(AccountType.SAVING).balance(dto.balance())
                .build();
    }


    @Override
    public TransactionDTO fromTransaction(@NotNull Transaction transaction) {
        return new TransactionDTO(transaction.getId(),transaction.getTransactionDate(),transaction.getType(),
                transaction.getAccountType(),transaction.getAmount(),transaction.getBalance(),transaction.getDescription(),
                transaction.getAccountId()
        );
    }
}
