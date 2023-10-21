package com.joelsalvi.quarkus.csvmanagement.infrastructure.exception;

public class CsvFileReadException extends RuntimeException {

    private final String message;

    public CsvFileReadException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
