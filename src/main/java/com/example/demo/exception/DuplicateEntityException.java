package com.example.demo.exception;

public class DuplicateEntityException extends RuntimeException{
    public DuplicateEntityException() {
        super("existe déjà");
    }
}
