package com.example.beck_spring_my_progect.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundOriginalUrlException extends RuntimeException {
    public NotFoundOriginalUrlException(String message) {
        super(message);
    }
}
