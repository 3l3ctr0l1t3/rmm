package com.ninja.rmm.exceptions;

import org.springframework.http.HttpStatus;

public class SubscriptionException extends ApplicationException {

    public SubscriptionException(String message, HttpStatus status) {
        super(message, status);
    }
}
