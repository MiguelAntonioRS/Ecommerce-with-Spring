package com.ecom.Ecommerce_SpringBoot.exception;

public class InsufficientException extends RuntimeException {

    public InsufficientException(String message) {
        super(message);
    }

    public InsufficientException(String message, Throwable cause) {
        super(message, cause);
    }

}
