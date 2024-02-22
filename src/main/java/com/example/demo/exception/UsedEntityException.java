package com.example.demo.exception;

public class UsedEntityException extends  RuntimeException{
    public UsedEntityException(String message) {
        super("Opération impossible," + message + " en cours d'utilisation");
    }
}
