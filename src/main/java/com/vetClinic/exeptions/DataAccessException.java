package com.vetClinic.exeptions;

public class DataAccessException extends  RuntimeException{
    public DataAccessException(String message) {
        super(message);
    }
}
