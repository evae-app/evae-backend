package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<ApiError> handleDuplicateRubriqueException(DuplicateEntityException ex) {
        ApiError apiError = new ApiError();
        apiError.setTimestamp(LocalDateTime.now());
        apiError.setStatus(HttpStatus.BAD_REQUEST.value());
        apiError.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        apiError.setMessage(ex.getMessage());

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(OrdreException.class)
    public ResponseEntity<ApiError> handleOrdreException(OrdreException ex) {
        ApiError apiError = new ApiError();
        apiError.setTimestamp(LocalDateTime.now());
        apiError.setStatus(HttpStatus.BAD_REQUEST.value());
        apiError.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        apiError.setMessage(ex.getMessage());

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}
