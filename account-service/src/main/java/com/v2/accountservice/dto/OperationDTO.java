package com.v2.accountservice.dto;

import com.v2.accountservice.enums.Currency;
import com.v2.accountservice.enums.OperationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OperationDTO(String id, LocalDateTime date, OperationType type, BigDecimal amount,
                           Currency currency, String description, String accountId) {
}
