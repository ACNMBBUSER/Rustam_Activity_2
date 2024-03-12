package com.v2.transactionservice.dto;

import com.v2.transactionservice.enums.AccountType;
import com.v2.transactionservice.enums.Currency;
import com.v2.transactionservice.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDTO(String id, LocalDateTime date, TransactionType type, AccountType accountType, BigDecimal amount,BigDecimal balance,
                              String description, String accountId) {
}
