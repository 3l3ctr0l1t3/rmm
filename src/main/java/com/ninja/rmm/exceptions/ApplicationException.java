package com.ninja.rmm.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class ApplicationException extends Exception {

    private final HttpStatus httpStatus;

    public ApplicationException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
