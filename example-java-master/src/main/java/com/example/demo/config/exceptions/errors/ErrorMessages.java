package com.example.demo.config.exceptions.errors;

import lombok.Getter;

@Getter
public enum ErrorMessages {

    RECORD_FOUND("This record exists in database"),
    CPF_EXCEPTION("CPF is not valid"),
    DESCRICAO_EXCEPTION("Description is not valid"),
    DATE_EXCEPTION("Date is not valid"),
    STATUS_EXCEPTION("Status is not valid"),
    RECORD_NOT_FOUND("This record doesn't exist in database"),
    EMPTY_DATA("This data can't be null or empty"),
    INVALID_SESSION("Invalid Session");

    private String message;

    ErrorMessages(String message) {
        this.message = message;
    }

}