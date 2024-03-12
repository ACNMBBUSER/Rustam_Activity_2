package com.v2.transactionservice.service;

import com.v2.transactionservice.dto.AccountDTO;
import com.v2.transactionservice.dto.CreditDTO;
import com.v2.transactionservice.dto.DebitDTO;
import com.v2.transactionservice.dto.TransactionDTO;
import com.v2.transactionservice.exception.AccountNotFoundException;
import com.v2.transactionservice.exception.TransactionNotFoundException;

public interface TransactionService {

    CreditDTO credit(CreditDTO creditDTO)throws AccountNotFoundException; //exception handling

    DebitDTO debit(DebitDTO debitDTO) throws AccountNotFoundException; //exception handling

    TransactionDTO getById(String id) throws TransactionNotFoundException;//exception handling

    AccountDTO getByAccountId(String id);
}
