package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "QUESTION", schema = "SPI")
public class Question {
    @Id
    @Column(name = "ID_QUESTION", nullable = false)
    private Integer id;

    @Column(name = "\"TYPE\"", nullable = false, length = 10)
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NO_ENSEIGNANT")
    private Enseignant noEnseignant;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_QUALIFICATIF", nullable = false)
    private Qualificatif idQualificatif;

    @Column(name = "INTITULE", nullable = false, length = 64)
    private String intitule;

}