package com.vetClinic.exeptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppError {
    private String errorMessage;
    private int httpStatusCode;


    public AppError(String errorMessage, int httpStatusCode) {
        this.errorMessage = errorMessage;
        this.httpStatusCode = httpStatusCode;
    }
}
