package com.v2.transactionservice.controller;


import com.v2.transactionservice.dto.AccountDTO;
import com.v2.transactionservice.dto.CreditDTO;
import com.v2.transactionservice.dto.DebitDTO;
import com.v2.transactionservice.exception.AccountNotFoundException;
import com.v2.transactionservice.exception.TransactionNotFoundException;
import com.v2.transactionservice.service.TransactionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/transaction")
public class TransactionRestController {

    private final TransactionService transactionService;

    public TransactionRestController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @PostMapping("/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws AccountNotFoundException {
        return transactionService.credit(creditDTO);
    }

    @PostMapping("/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws AccountNotFoundException{
        return transactionService.debit(debitDTO);
    }

    @GetMapping("/get/{id}")
    public AccountDTO getByAccountId(@PathVariable String id) throws TransactionNotFoundException {
        return transactionService.getByAccountId(id);
    }



}
