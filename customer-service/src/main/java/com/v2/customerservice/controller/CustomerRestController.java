package com.v2.customerservice.controller;

import com.v2.customerservice.dto.CustomerDTO;
import com.v2.customerservice.dto.CustomerPageDTO;
import com.v2.customerservice.exception.CinAlreadyExistException;
import com.v2.customerservice.exception.CustomerNotFoundException;
import com.v2.customerservice.exception.EmailAlreadyExistException;
import com.v2.customerservice.exception.PhoneAlreadyExistException;
import com.v2.customerservice.service.CustomerService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/customers")
public class CustomerRestController {

    private final CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/save")
    public CustomerDTO save(@RequestBody CustomerDTO customerDTO) throws CinAlreadyExistException, EmailAlreadyExistException, PhoneAlreadyExistException {
        return customerService.save(customerDTO);
    }

    @PutMapping("/update/{id}")
    public CustomerDTO update(@PathVariable String id, @RequestBody  CustomerDTO customerDTO) throws CustomerNotFoundException, CinAlreadyExistException, EmailAlreadyExistException, PhoneAlreadyExistException{
        return customerService.update(id, customerDTO);
    }

    @GetMapping("/get/{id}")
    public CustomerDTO getById(@PathVariable String id) throws CustomerNotFoundException {
        return customerService.getById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable String id){
        customerService.deleteById(id);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(@NotNull Exception exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
