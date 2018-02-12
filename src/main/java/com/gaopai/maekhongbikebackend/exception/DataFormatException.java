package com.gaopai.maekhongbikebackend.exception;

public class DataFormatException extends RuntimeException {

    private static final long serialVersionUID = 1144290428893103435L;

    public DataFormatException() {
        super();
    }

    public DataFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataFormatException(String message) {
        super(message);
    }

    public DataFormatException(Throwable cause) {
        super(cause);
    }
}
