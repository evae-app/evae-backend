package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "AUTHENTIFICATION", schema = "SPI")
public class Authentification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUTHENTIFICATION_id_gen")
    @SequenceGenerator(name = "AUTHENTIFICATION_id_gen", sequenceName = "AUT_SEQ", allocationSize = 1)
    @Column(name = "ID_CONNECTION", nullable = false)
    private Long id;

    @Column(name = "ROLE", nullable = false, length = 5)
    private String role;

    @Column(name = "LOGIN_CONNECTION", nullable = false, length = 64)
    private String loginConnection;

    @Column(name = "PSEUDO_CONNECTION", length = 240)
    private String pseudoConnection;

    @Column(name = "MOT_PASSE", length = 32)
    private String motPasse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NO_ENSEIGNANT")
    private Enseignant noEnseignant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NO_ETUDIANT")
    private Etudiant noEtudiant;

}