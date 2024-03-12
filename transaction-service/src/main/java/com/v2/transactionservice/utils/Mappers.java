package com.v2.transactionservice.utils;

import com.v2.transactionservice.dto.TransactionDTO;
import com.v2.transactionservice.entity.Transaction;

public interface Mappers {

    Transaction fromTransactionDTO(TransactionDTO dto);

    TransactionDTO fromTransaction(Transaction transaction);
}
