package com.example.demo.exception;

public class UsedEntityException extends  RuntimeException{
    public UsedEntityException() {
        super(" Entité en cours d'utilisation");
    }
}
