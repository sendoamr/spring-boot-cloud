package com.sendoa.opendata.error;



public class ApplicationException extends RuntimeException {
    public ApplicationException(String message) {
        super(message);
    }
}