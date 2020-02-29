package com.example.demo.domain.services;

import com.example.demo.config.exceptions.CpfException;
import com.example.demo.config.exceptions.RecordFoundException;
import com.example.demo.config.exceptions.errors.ErrorMessages;
import com.example.demo.domain.utils.CnpjCpf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class ClienteValidateService {

    @Autowired
    private ClienteService service;

    protected void validate(String cpf) throws RecordFoundException, CpfException {
        /* Se encontrou o CPF */
        if (service.find(cpf) != null)
            throw new RecordFoundException(ErrorMessages.RECORD_FOUND.getMessage());
        else {
            if (!CnpjCpf.validate(cpf))
                throw new CpfException(ErrorMessages.CPF_EXCEPTION.getMessage());
        }
    }
}