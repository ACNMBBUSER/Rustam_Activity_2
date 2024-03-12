package com.v2.transactionservice.dto;

import com.v2.transactionservice.enums.AccountStatus;
import com.v2.transactionservice.enums.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountDTO(String id, BigDecimal balance, Currency currency,
                         AccountStatus status, LocalDateTime creation, LocalDateTime lastUpdate) {
}
