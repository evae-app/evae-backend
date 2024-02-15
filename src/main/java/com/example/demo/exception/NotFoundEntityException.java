package com.example.demo.exception;

public class NotFoundEntityException extends RuntimeException {
    public NotFoundEntityException() {
        super(" Entité non trouvé");
    }
}
