package com.v2.transactionservice.repository;

import com.v2.transactionservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    @Query("select a from Transaction a where a.accountId = ?1")
    Transaction findByAccountId(String accountId);
}
