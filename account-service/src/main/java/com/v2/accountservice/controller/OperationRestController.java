package com.v2.accountservice.controller;

import com.v2.accountservice.dto.CreditDTO;
import com.v2.accountservice.dto.DebitDTO;
import com.v2.accountservice.dto.OperationDTO;
import com.v2.accountservice.exception.AccountNotActivatedException;
import com.v2.accountservice.exception.AccountNotFoundException;
import com.v2.accountservice.exception.BalanceNotSufficientException;
import com.v2.accountservice.exception.OperationNotFoundException;
import com.v2.accountservice.service.OperationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/operations")
public class OperationRestController {

    private final OperationService operationService;

    public OperationRestController(OperationService operationService) {
        this.operationService = operationService;
    }


    @PostMapping("/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws AccountNotFoundException, BalanceNotSufficientException, AccountNotActivatedException {
        return operationService.credit(creditDTO);
    }

    @PostMapping("/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws AccountNotFoundException, BalanceNotSufficientException, AccountNotActivatedException{
        return operationService.debit(debitDTO);
    }

    @GetMapping("/get/{id}")
    public OperationDTO getById(@PathVariable String id) throws OperationNotFoundException {
        return operationService.getById(id);
    }

}
