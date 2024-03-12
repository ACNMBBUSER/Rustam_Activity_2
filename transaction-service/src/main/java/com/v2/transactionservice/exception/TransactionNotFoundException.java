package com.v2.transactionservice.exception;

public class TransactionNotFoundException extends Exception{
    public TransactionNotFoundException(String message) {
        super(message);
    }

}
