package com.ninja.rmm.exceptions;

import org.springframework.http.HttpStatus;

public class DeviceException extends ApplicationException {

    public DeviceException(String message, HttpStatus status) {
        super(message, status);
    }
}
