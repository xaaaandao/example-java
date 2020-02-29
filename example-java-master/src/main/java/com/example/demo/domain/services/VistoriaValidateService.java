package com.example.demo.domain.services;

import com.example.demo.config.exceptions.DateException;
import com.example.demo.config.exceptions.errors.ErrorMessages;
import com.example.demo.domain.utils.Data;
import org.springframework.stereotype.Service;

@Service
public class VistoriaValidateService {
    protected void validate(String data) throws DateException {
        if(!Data.dateValidator(data))
            throw new DateException(ErrorMessages.DATE_EXCEPTION.getMessage());
    }
}
