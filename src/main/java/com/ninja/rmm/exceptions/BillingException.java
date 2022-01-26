package com.ninja.rmm.exceptions;

import org.springframework.http.HttpStatus;

public class BillingException extends ApplicationException{

    public BillingException(String message, HttpStatus status) {
        super(message, status);
    }
}
