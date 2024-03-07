package com.v2.accountservice.dto;

import com.v2.accountservice.enums.Currency;

import java.math.BigDecimal;
import java.util.List;

public record HistoryDTO(String customerId, String accountId, Currency currency,
                         BigDecimal balance, int currentPage, int totalPages,
                         int pageSize, List<OperationDTO> operations) {
}
