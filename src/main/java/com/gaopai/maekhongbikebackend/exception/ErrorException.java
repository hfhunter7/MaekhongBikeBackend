package com.gaopai.maekhongbikebackend.exception;

public class ErrorException extends Exception {

    private static final long serialVersionUID = -2125138982250891397L;

    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }
    public ErrorException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
    public ErrorException() {
        super();
    }
}