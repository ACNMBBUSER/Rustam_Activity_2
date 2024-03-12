package com.v2.customerservice.service;

import com.v2.customerservice.dto.CustomerDTO;
import com.v2.customerservice.dto.CustomerPageDTO;
import com.v2.customerservice.entity.Customer;
import com.v2.customerservice.enums.Sex;
import com.v2.customerservice.exception.CinAlreadyExistException;
import com.v2.customerservice.exception.CustomerNotFoundException;
import com.v2.customerservice.exception.EmailAlreadyExistException;
import com.v2.customerservice.exception.PhoneAlreadyExistException;
import com.v2.customerservice.repository.CustomerRepository;
import com.v2.customerservice.service.implementation.CustomerServiceImpl;
import com.v2.customerservice.util.Mappers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CustomerServiceTest {
    @Mock
    private CustomerRepository repository;

    @Mock
    private Mappers mappers;

    @InjectMocks
    private CustomerServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new CustomerServiceImpl(repository, mappers);
    }

    @Test
    void save() throws PhoneAlreadyExistException, CinAlreadyExistException, EmailAlreadyExistException {
        String uuid = UUID.randomUUID().toString();
        CustomerDTO customerDTO = new CustomerDTO(
                uuid, "john", "doe", "world", new Date(), "world",
                Sex.M, uuid, uuid, uuid, LocalDateTime.now(), null);
        Customer customer = new Customer(
                uuid, "john", "doe", "world", new Date(), "world",
                Sex.M, uuid, uuid, uuid, LocalDateTime.now(), null
        );
        when(repository.checkIfCinExists(customerDTO.cin())).thenReturn(false);
        when(repository.checkIfEmailExists(customerDTO.email())).thenReturn(false);
        when(repository.checkIfPhoneExists(customerDTO.phone())).thenReturn(false);
        when(repository.save(customer)).thenReturn(customer);
        when(mappers.fromCustomerDTO(customerDTO)).thenReturn(customer);
        service.save(customerDTO);
        verify(repository, times(1)).save(any());
    }

    @Test
    void update() throws PhoneAlreadyExistException, CinAlreadyExistException, CustomerNotFoundException, EmailAlreadyExistException {
        String customerId = "123";
        Customer existingCustomer = Customer.builder()
                .id(customerId).firstname("John").name("Doe").dateOfBirth(new Date()).nationality("Nationality")
                .placeOfBirth("GABON").sex(Sex.M).cin("oldCin").phone("oldPhone").email("oldEmail")
                .creation(LocalDateTime.now()).lastUpdate(null).build();

        CustomerDTO request = new CustomerDTO(
                customerId, "John", "Doe", "GABON", new Date(),
                "Nationality", Sex.M, "cin", "email", "phone", LocalDateTime.now(), null);

        when(mappers.fromCustomerDTO(request)).thenReturn(
                Customer.builder()
                        .id(customerId).firstname("John").name("Doe").dateOfBirth(new Date()).nationality("Nationality").placeOfBirth("GABON").sex(Sex.M).cin("cin")
                        .phone("phone").email("email").creation(LocalDateTime.now()).lastUpdate(null).build()
        );
        when(mappers.fromCustomer(any())).thenReturn(
                new CustomerDTO(
                        customerId, "Marvin", "Maven", "FRANCE", new Date(),
                        "Nationality", Sex.M, "cin", "email", "phone", LocalDateTime.now(), null)
        );
        when(repository.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(repository.checkIfCinExists(anyString())).thenReturn(false);
        when(repository.checkIfEmailExists(anyString())).thenReturn(false);
        when(repository.checkIfPhoneExists(anyString())).thenReturn(false);
        CustomerDTO response = service.update(customerId, request);
        verify(repository, times(1)).save(any());
        assertNotNull(response);
        assertEquals(response.id(), request.id());
        assertNotEquals(response.name(), request.name());
        assertNotEquals(response.firstname(), request.firstname());
    }

    @Test
    void getById() throws CustomerNotFoundException {
        String uuid = UUID.randomUUID().toString();
        Customer customer = new Customer();
        customer.setId(uuid);
        customer.setFirstname("John");
        customer.setName("Doe");
        customer.setNationality("WORLD");
        customer.setSex(Sex.M);
        customer.setDateOfBirth(new Date());
        customer.setPhone(uuid);
        customer.setCin(uuid);
        customer.setEmail(uuid);
        customer.setPlaceOfBirth("WORLD");
        customer.setCreation(LocalDateTime.now());
        when(repository.findById(uuid)).thenReturn(Optional.of(customer));
        when(mappers.fromCustomer(customer)).thenReturn(
                new CustomerDTO(
                        uuid, "John", "Doe", "FRANCE", new Date(),
                        "Nationality", Sex.M, uuid, uuid, uuid, LocalDateTime.now(), null)
        );

        CustomerDTO customerDTO = service.getById(uuid);

        assertNotNull(customerDTO);
        assertEquals(customerDTO.id(), uuid);
        assertEquals(customerDTO.email(), customer.getEmail());
        assertEquals(customerDTO.phone(), customer.getPhone());
        assertEquals(customerDTO.cin(), customer.getPhone());
        assertEquals(customerDTO.name(), customer.getName());
        assertEquals(customerDTO.firstname(), customer.getFirstname());
    }


    @Test
    void deleteById() {
        String customerId = "1";
        service.deleteById(customerId);
        verify(repository, times(1)).deleteById(customerId);
    }
}