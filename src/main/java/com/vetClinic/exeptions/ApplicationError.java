package com.vetClinic.exeptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApplicationError {
    private String errorMessage;
    private int httpStatusCode;

    public ApplicationError(String errorMessage, int httpStatusCode) {
        this.errorMessage = errorMessage;
        this.httpStatusCode = httpStatusCode;
    }
}
