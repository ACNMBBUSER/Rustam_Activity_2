package com.v2.accountservice.service;

import com.v2.accountservice.dto.CreditDTO;
import com.v2.accountservice.dto.DebitDTO;
import com.v2.accountservice.dto.HistoryDTO;
import com.v2.accountservice.dto.OperationDTO;
import com.v2.accountservice.exception.AccountNotActivatedException;
import com.v2.accountservice.exception.AccountNotFoundException;
import com.v2.accountservice.exception.BalanceNotSufficientException;
import com.v2.accountservice.exception.OperationNotFoundException;

public interface OperationService {
    /**
     * Credit funds to an account.
     *
     */
    CreditDTO credit(CreditDTO creditDTO) throws AccountNotFoundException, BalanceNotSufficientException, AccountNotActivatedException;

    /**
     * Debit funds from an account.
     *
     * @param debitDTO The debit details.
     */
    DebitDTO debit(DebitDTO debitDTO) throws AccountNotFoundException, BalanceNotSufficientException, AccountNotActivatedException;

    /**
     * Retrieve an operation by its ID.
     *
     */
    OperationDTO getById(String id) throws OperationNotFoundException;

//    /**
//     * Retrieve the history of operations for a specific account.
//     *
//     * @param accountId The ID of the account to retrieve the history for.
//     * @param page The page number for pagination.
//     * @param size The number of operations per page.
//     * @return The history of operations for the account.
//     * @throws AccountNotFoundException if account not found
//     *
//     */

    HistoryDTO getHistory(String accountId, int page, int size) throws AccountNotFoundException;
    }
