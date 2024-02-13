package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "QUESTION")
public class Question {
    @Id
    @Column(name = "ID_QUESTION", nullable = false)
    private Integer id;

    @Column(name = "\"TYPE\"", nullable = false, length = 10)
    private String type;

    @Column(name = "NO_ENSEIGNANT")
    private Short noEnseignant;

    @Column(name = "ID_QUALIFICATIF", nullable = false)
    private Integer idQualificatif;

    @Column(name = "INTITULE", nullable = false, length = 64)
    private String intitule;

}