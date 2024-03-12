package com.v2.customerservice.service.implementation;

import com.v2.customerservice.dto.CustomerDTO;
import com.v2.customerservice.dto.CustomerPageDTO;
import com.v2.customerservice.entity.Customer;
import com.v2.customerservice.exception.CinAlreadyExistException;
import com.v2.customerservice.exception.CustomerNotFoundException;
import com.v2.customerservice.exception.EmailAlreadyExistException;
import com.v2.customerservice.exception.PhoneAlreadyExistException;
import com.v2.customerservice.repository.CustomerRepository;
import com.v2.customerservice.service.CustomerService;
import com.v2.customerservice.util.Mappers;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {


    private static final String ALREADY_EXIST = "' already exist.";
    private static final String CUSTOMER_WITH_EMAIL = "A customer with email :'";
    private static final String CUSTOMER_WITH_PHONE = "A customer with phone :'";
    private static final String CUSTOMER_WITH_CIN = "A customer with cin :'";

    private final CustomerRepository customerRepository;
    private final Mappers mappers;

    public CustomerServiceImpl(CustomerRepository customerRepository, Mappers mappers) {
        this.customerRepository = customerRepository;
        this.mappers = mappers;
    }

    @Transactional
    @Override
    public CustomerDTO save(CustomerDTO customerDTO) throws CinAlreadyExistException, EmailAlreadyExistException, PhoneAlreadyExistException {
        log.info("In save() :");
        checkIfCinOrEmailOrPhoneExistBeforeSave(customerDTO);
        Customer customer = mappers.fromCustomerDTO(customerDTO);
        customer.setLastUpdate(null);
        customer.setId(UUID.randomUUID().toString());
        customer.setCreation(LocalDateTime.now());
        Customer customerSaved = customerRepository.save(customer);
        log.info("customer saved successfully");
        return mappers.fromCustomer(customerSaved);
    }

    @Transactional
    @Override
    public CustomerDTO update(String id, CustomerDTO customerDTO) throws CustomerNotFoundException, CinAlreadyExistException, EmailAlreadyExistException, PhoneAlreadyExistException {
        log.info("In update() :");
        Customer customer = customerRepository.findById(id)
                .orElseThrow( ()-> new CustomerNotFoundException("customer you try to update with id = '"+id+"' not found."));
        checkIfCinOrEmailOrPhoneExistBeforeUpdate(customer, customerDTO);
        updateCustomerFields(customer, customerDTO);
        Customer customerUpdate = customerRepository.save(customer);
        log.info("Customer updated successfully");
        return mappers.fromCustomer(customerUpdate);
    }

    @Override
    public CustomerDTO getById(String id) throws CustomerNotFoundException {
        log.info("In getById() :");
        Customer customer = customerRepository.findById(id)
                .orElseThrow( ()-> new CustomerNotFoundException("customer with id = '"+id+"' not found."));
        log.info("customer with id = '"+id+"' found successfully.");
        return mappers.fromCustomer(customer);
    }

    @Override
    public void deleteById(String id) {
        log.info("In deleteById() :");
        customerRepository.deleteById(id);
        log.info("customer deleted");
    }

    private void updateCustomerFields(@NotNull Customer customer, @NotNull CustomerDTO customerDTO){
        customer.setFirstname(customerDTO.firstname());
        customer.setName(customerDTO.name());
        customer.setSex(customerDTO.sex());
        customer.setPlaceOfBirth(customerDTO.placeOfBirth());
        customer.setNationality(customerDTO.nationality());
        customer.setCin(customerDTO.cin());
        customer.setEmail(customerDTO.email());
        customer.setPhone(customerDTO.phone());
        customer.setLastUpdate(LocalDateTime.now());
    }

    private void checkIfCinOrEmailOrPhoneExistBeforeSave(@NotNull CustomerDTO customerDTO) throws CinAlreadyExistException, EmailAlreadyExistException, PhoneAlreadyExistException {
        checkIfCinExists(customerDTO.cin());
        checkIfEmailExists(customerDTO.email());
        checkIfPhoneExists(customerDTO.phone());
    }

    private void checkIfCinOrEmailOrPhoneExistBeforeUpdate(@NotNull Customer customer, @NotNull CustomerDTO customerDTO) throws CinAlreadyExistException, EmailAlreadyExistException, PhoneAlreadyExistException {
        if (!customer.getCin().equals(customerDTO.cin())){
            checkIfCinExists(customerDTO.cin());
        }
        if(!customer.getEmail().equals(customerDTO.email())){
            checkIfEmailExists(customerDTO.email());
        }
        if(!customer.getPhone().equals(customerDTO.phone())){
            checkIfPhoneExists(customerDTO.phone());
        }
    }

    private void checkIfCinExists(String cin) throws CinAlreadyExistException {
        if (Boolean.TRUE.equals(customerRepository.checkIfCinExists(cin))) {
            throw new CinAlreadyExistException(CUSTOMER_WITH_CIN + cin + ALREADY_EXIST);
        }
    }

    private void checkIfEmailExists(String email) throws EmailAlreadyExistException {
        if (Boolean.TRUE.equals(customerRepository.checkIfEmailExists(email))) {
            throw new EmailAlreadyExistException(CUSTOMER_WITH_EMAIL + email + ALREADY_EXIST);
        }
    }

    private void checkIfPhoneExists(String phone) throws PhoneAlreadyExistException {
        if (Boolean.TRUE.equals(customerRepository.checkIfPhoneExists(phone))) {
            throw new PhoneAlreadyExistException(CUSTOMER_WITH_PHONE + phone + ALREADY_EXIST);
        }
    }
}
