package com.vetClinic.exeptions;

public class ForbiddenContentException extends  RuntimeException{
    public ForbiddenContentException(String message) {
        super(message);
    }
}
