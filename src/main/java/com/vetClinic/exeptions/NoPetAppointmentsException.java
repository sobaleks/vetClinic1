package com.vetClinic.exeptions;

public class NoPetAppointmentsException extends RuntimeException {
    public NoPetAppointmentsException(String message) {
        super(message);
    }
}
