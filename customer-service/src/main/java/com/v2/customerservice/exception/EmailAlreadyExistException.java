package com.v2.customerservice.exception;


public class EmailAlreadyExistException extends Exception{

    public EmailAlreadyExistException(String message) {
        super(message);
    }
}
