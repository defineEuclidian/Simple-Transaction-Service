package dev.transactionservice.models;

import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.http.HttpStatus;

/**
 * InternalException class that is a child of RuntimeException, used to throw exceptions in the controllers and also allow for testing the exceptions.
 * 
 * <p> Annotated with the Unprocessable Entity (HTTP 422) HTTP response code. </p>
 */
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InternalException extends RuntimeException {
    
    /**
     * Parameterized constructor for InternalException.
     * 
     * @param message String
     */
    public InternalException(String message) {
        super(message);
    }
}
