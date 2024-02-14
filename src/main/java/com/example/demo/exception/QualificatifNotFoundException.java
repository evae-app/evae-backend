package com.example.demo.exception;

public class QualificatifNotFoundException extends RuntimeException{
    public QualificatifNotFoundException(Integer id){
        super("Qualificatif non trouvee");
    }
}