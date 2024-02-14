package com.example.demo.exception;

public class QualificatifInUseException extends RuntimeException{
    public QualificatifInUseException(Integer id) {
        super("Qualificatif with id " + id + " is in use and cannot be deleted.");
}
}