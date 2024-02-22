package com.service.api.framework.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class AmazonClientException extends BaseException {
    private static final long serialVersionUID = 1L;

    public AmazonClientException(Throwable rootCause) {
        super(rootCause);
    }

    public AmazonClientException(String message) {
        super(message);
    }

    public AmazonClientException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

    public AmazonClientException(String message, HttpStatus statusCode) {
        super(message, statusCode);
    }

    public AmazonClientException(String message, HttpStatus statusCode, List<String> errorMessages) {
        super(message, statusCode, errorMessages);
    }
}
