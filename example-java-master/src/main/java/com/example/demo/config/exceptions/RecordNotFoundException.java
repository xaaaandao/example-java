package com.example.demo.config.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Data not found")
public class RecordNotFoundException extends Exception {

    public RecordNotFoundException(String message) {
        super(message);
    }

}
