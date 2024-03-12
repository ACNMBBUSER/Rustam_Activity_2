package com.v2.transactionservice.controller;


import com.v2.transactionservice.dto.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ACCOUNT-SERVICE", url = "http://localhost:8886")
public interface AccountRestController {

    @GetMapping("/bank/v2/accounts/get/{id}")
    AccountDTO getById(@PathVariable String id);
}
