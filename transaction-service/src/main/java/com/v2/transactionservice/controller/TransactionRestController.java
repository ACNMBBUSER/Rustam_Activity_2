package com.v2.transactionservice.controller;


import com.v2.transactionservice.dto.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "TRANSACTION-SERVICE")
public interface TransactionRestController {

    @GetMapping("/bank/v2/account/get/{id}")
    AccountDTO getById(@PathVariable String id);
}
