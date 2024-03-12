package com.v2.transactionservice.dto;

import com.v2.transactionservice.enums.AccountType;

import java.math.BigDecimal;

public record DebitDTO(String id, String description, BigDecimal amount, AccountType accountType) {
}
