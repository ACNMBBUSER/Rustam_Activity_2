package com.v2.transactionservice.entity;

import com.v2.transactionservice.enums.AccountType;
import com.v2.transactionservice.enums.Currency;
import com.v2.transactionservice.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Transaction {

    @Id
    private String id;

    @Column(updatable = false, nullable = false)
    private String accountId;

    @Enumerated(EnumType.STRING)
    @Column(updatable = false, nullable = false)
    private AccountType accountType;

    @Column(updatable = false, nullable = false)
    private LocalDateTime transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(updatable = false, nullable = false)
    private TransactionType type;

    @Column(updatable = false, nullable = false)
    private BigDecimal amount;

    @Column(updatable = false, nullable = false)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(updatable = false, nullable = true)
    private Currency currency;

    @Column(updatable = false, nullable = false)
    private String description;

}
