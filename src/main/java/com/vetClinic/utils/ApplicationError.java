package com.vetClinic.utils;

import lombok.Data;

@Data
public class ApplicationError {

    private String errorMessage;
    private Integer httpStatus;

    public ApplicationError(String errorMessage, Integer httpStatus) {
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}
