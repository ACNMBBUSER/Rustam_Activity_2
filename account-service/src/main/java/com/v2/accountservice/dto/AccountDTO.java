package com.v2.accountservice.dto;

import com.v2.accountservice.enums.AccountStatus;
import com.v2.accountservice.enums.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountDTO(String id, String customerId, BigDecimal balance, Currency currency,
                         AccountStatus status, LocalDateTime creation, LocalDateTime lastUpdate) {
}
