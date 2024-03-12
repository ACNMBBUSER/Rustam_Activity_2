package com.v2.customerservice.repository;

import com.v2.customerservice.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, String> {

    /**
     * Checks if a customer with the given national ID (CIN) exists.
     */
    @Query("select case when count(c)>0 then true else false END from Customer c where c.cin=?1")
    Boolean checkIfCinExists(String cin);

    /**
     * Checks if a customer with the given phone number exists.
     */
    @Query("select case when count(c)>0 then true else false END from Customer c where c.phone=?1")
    Boolean checkIfPhoneExists(String phone);

    /**
     * Checks if a customer with the given email address exists.
     */
    @Query("select case when count(c)>0 then true else false END from Customer c where c.email=?1")
    Boolean checkIfEmailExists(String email);

}
