package com.vetClinic.exeptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;
import java.util.Set;

@ControllerAdvice
public class ExceptionResolver {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> validationHandler(ConstraintViolationException e) {
        logger.warn("Validation error: " + e.getConstraintViolations());
        StringBuilder errorMessage = new StringBuilder("Data filling error: \n");
        Set<ConstraintViolation<?>> hashSet = e.getConstraintViolations();
        for (ConstraintViolation c : hashSet) {
            errorMessage.append("Field error ").append(c.getPropertyPath());
            errorMessage.append(", ").append(c.getMessage()).append(".\n");
        }
        return new ResponseEntity<>(new ApplicationError(errorMessage.toString(),
                HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> nullPointerHandler(NoSuchElementException e) {
        logger.warn("Value passed null: " + e.fillInStackTrace());
        return new ResponseEntity<>(new ApplicationError("The object you were looking for was not found",
                HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ForbiddenContentException.class)
    public ResponseEntity<ApplicationError> forbiddenContentHandler(ForbiddenContentException e) {
        logger.warn("trying to send request for forbidden object" + e.getLocalizedMessage());
        return new ResponseEntity<>(new ApplicationError("You don't have permission for this request, " +
                e.getLocalizedMessage(), HttpStatus.FORBIDDEN.value()), HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ApplicationError> objectNotFoundHandler(ObjectNotFoundException e) {
        logger.warn("the requested object was not found" + e.getLocalizedMessage());
        return new ResponseEntity<>(new ApplicationError("The requested object was not found, " +
                e.getLocalizedMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApplicationError> dataAccessHandler(DataAccessException e) {
        logger.warn("data error: " + e.getLocalizedMessage());
        return new ResponseEntity<>(new ApplicationError("registration error, " + e.getLocalizedMessage(),
                HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }
}
