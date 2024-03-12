package com.v2.customerservice.service;

import com.v2.customerservice.dto.CustomerDTO;
import com.v2.customerservice.dto.CustomerPageDTO;
import com.v2.customerservice.exception.CinAlreadyExistException;
import com.v2.customerservice.exception.CustomerNotFoundException;
import com.v2.customerservice.exception.EmailAlreadyExistException;
import com.v2.customerservice.exception.PhoneAlreadyExistException;


public interface CustomerService {


    CustomerDTO save(CustomerDTO customerDTO) throws CinAlreadyExistException, EmailAlreadyExistException, PhoneAlreadyExistException;


    CustomerDTO update(String id, CustomerDTO customerDTO) throws CustomerNotFoundException, CinAlreadyExistException, EmailAlreadyExistException, PhoneAlreadyExistException;


    CustomerDTO getById(String id) throws CustomerNotFoundException;


    void deleteById(String id);
}
