package com.ninja.rmm.exceptions;

import org.springframework.http.HttpStatus;

public class CustomerException extends ApplicationException{

    public CustomerException(String message, HttpStatus status) {
        super(message, status);
    }
}
