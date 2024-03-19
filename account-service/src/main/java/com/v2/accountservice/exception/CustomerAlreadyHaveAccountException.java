package com.v2.accountservice.exception;

public class CustomerAlreadyHaveAccountException extends Exception{

    public CustomerAlreadyHaveAccountException(String message) {
        super(message);
    }
}
