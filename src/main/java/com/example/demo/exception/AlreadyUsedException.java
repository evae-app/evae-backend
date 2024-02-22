package com.example.demo.exception;

public class AlreadyUsedException extends RuntimeException{
    public AlreadyUsedException(String message) {
        super("Opération impossible , " + message + " en cours d'utilisation");
    }
}