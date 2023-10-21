package com.joelsalvi.quarkus.csvmanagement.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

    private int errorCode;
    private String message;

    public Error() {}

    public Error(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    @JsonProperty("errorCode")
    public int getErrorCode() {
        return errorCode;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Error{" +
                "errorCode=" + errorCode +
                ", message='" + message + '\'' +
                '}';
    }
}
