package com.v2.transactionservice.dto;

import com.v2.transactionservice.enums.AccountType;
import com.v2.transactionservice.enums.Action;
import com.v2.transactionservice.enums.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ActionDTO(String id, LocalDateTime date, Action type, AccountType accountType, BigDecimal creditAmount, BigDecimal debitAmount,
                        Currency currency, String description, String accountId) {
}
