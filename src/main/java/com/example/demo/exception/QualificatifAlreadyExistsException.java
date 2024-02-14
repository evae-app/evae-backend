package com.example.demo.exception;

public class QualificatifAlreadyExistsException extends RuntimeException {
    public QualificatifAlreadyExistsException(String maximal, String minimal) {
        super("Qualificatif avec maximal '" + maximal + "' or minimal '" + minimal + "' exists déjà.");
}
}