package com.example.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnAuthException extends RuntimeException {
    public UnAuthException(String message) {
        super(message);
    }
}
