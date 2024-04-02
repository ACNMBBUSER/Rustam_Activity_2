package com.v2.accountservice.dto;

import java.math.BigDecimal;
import java.util.List;

public record HistoryDTO(String customerId, String accountId,
                         BigDecimal balance, int currentPage, int totalPages,
                         int pageSize, List<OperationDTO> operations) {
}
