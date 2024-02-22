package com.service.api.framework.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class AmazonServiceException  extends BaseException {
    private static final long serialVersionUID = 1L;

    public AmazonServiceException(Throwable rootCause) {
        super(rootCause);
    }

    public AmazonServiceException(String message) {
        super(message);
    }

    public AmazonServiceException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

    public AmazonServiceException(String message, HttpStatus statusCode) {
        super(message, statusCode);
    }

    public AmazonServiceException(String message, HttpStatus statusCode, List<String> errorMessages) {
        super(message, statusCode, errorMessages);
    }
}
