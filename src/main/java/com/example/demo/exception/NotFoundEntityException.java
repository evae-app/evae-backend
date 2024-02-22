package com.example.demo.exception;

public class NotFoundEntityException extends RuntimeException {
    public NotFoundEntityException(String message) {
        super("Opération impossible " + message +" est introuvable");
    }
}
