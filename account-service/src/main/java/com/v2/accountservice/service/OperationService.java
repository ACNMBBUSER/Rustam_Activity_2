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
     * @param creditDTO The credit details.
     * @return The updated account details after the credit operation.
     * @throws AccountNotFoundException If the account for the credit operation is not found.
     * @throws BalanceNotSufficientException If the account balance is not sufficient for the credit operation.
     * @throws AccountNotActivatedException If account status is not set to ACTIVATED
     */
    CreditDTO credit(CreditDTO creditDTO) throws AccountNotFoundException, BalanceNotSufficientException, AccountNotActivatedException;

    /**
     * Debit funds from an account.
     *
     * @param debitDTO The debit details.
     * @return The updated account details after the debit operation.
     * @throws AccountNotFoundException If the account for the debit operation is not found.
     * @throws BalanceNotSufficientException If the account balance is not sufficient for the debit operation.
     * @throws AccountNotActivatedException If account status is not set to ACTIVATED
     */
    DebitDTO debit(DebitDTO debitDTO) throws AccountNotFoundException, BalanceNotSufficientException, AccountNotActivatedException;

    /**
     * Retrieve an operation by its ID.
     *
     * @param id The ID of the operation to retrieve.
     * @return The operation details.
     * @throws OperationNotFoundException If the operation with the given ID is not found.
     */
    OperationDTO getById(String id) throws OperationNotFoundException;

    /**
     * Retrieve the history of operations for a specific account.
     *
     * @param accountId The ID of the account to retrieve the history for.
     * @param page The page number for pagination.
     * @param size The number of operations per page.
     * @return The history of operations for the account.
     * @throws AccountNotFoundException if account not found
     */
    HistoryDTO getHistory(String accountId, int page, int size) throws AccountNotFoundException;
}
