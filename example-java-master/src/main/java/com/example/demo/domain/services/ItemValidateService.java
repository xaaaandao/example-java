package com.example.demo.domain.services;

import com.example.demo.config.exceptions.DescricaoException;
import com.example.demo.config.exceptions.RecordFoundException;
import com.example.demo.config.exceptions.StatusException;
import com.example.demo.config.exceptions.errors.ErrorMessages;
import com.example.demo.domain.utils.DescricaoStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class ItemValidateService {

    @Autowired
    private ItemService service;

    protected void validateDescricao(String descricao) throws DescricaoException, RecordFoundException {
        if (service.find(descricao) != null)
            throw new RecordFoundException(ErrorMessages.RECORD_FOUND.getMessage());
        if(!DescricaoStatus.validateDescricao(descricao))
            throw new DescricaoException(ErrorMessages.DESCRICAO_EXCEPTION.getMessage());
    }

    protected void validateStatus(String status) throws StatusException {
        if(!DescricaoStatus.validateStatus(status))
            throw new StatusException(ErrorMessages.STATUS_EXCEPTION.getMessage());
    }
}
