package com.example.demo.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<ExceptionMessage> duplicateEntityExceptionHandler(HttpServletRequest request, DuplicateEntityException exception) {
        ExceptionMessage message = new ExceptionMessage.Builder()
                .date(LocalDateTime.now().toString())
                .path(request.getRequestURI().toString() + "?" + request.getQueryString())
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyUsedException.class)
    public ResponseEntity<ExceptionMessage > handleAlreadyUsedException(HttpServletRequest request, AlreadyUsedException exception) {
        ExceptionMessage message = new ExceptionMessage.Builder()
                .date(LocalDateTime.now().toString())
                .path(request.getRequestURI().toString() + "?" + request.getQueryString())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(QualificatifAlreadyExistsException.class)
    public ResponseEntity<ExceptionMessage > handleQualificatifAlreadyExistsException(HttpServletRequest request,QualificatifAlreadyExistsException exception) {
        ExceptionMessage message = new ExceptionMessage.Builder()
                .date(LocalDateTime.now().toString())
                .path(request.getRequestURI().toString() + "?" + request.getQueryString())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrdreException.class)
    public ResponseEntity<ExceptionMessage > handleOrdreException(HttpServletRequest request,OrdreException exception) {
        ExceptionMessage message = new ExceptionMessage.Builder()
                .date(LocalDateTime.now().toString())
                .path(request.getRequestURI().toString() + "?" + request.getQueryString())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);
    }


}
