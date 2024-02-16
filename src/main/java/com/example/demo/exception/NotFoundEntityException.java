package com.example.demo.exception;

public class NotFoundEntityException extends RuntimeException {
    public NotFoundEntityException() {
        super(" Veuillez verifier vos données, entité introuvable");
    }
}
