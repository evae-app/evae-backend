package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "CANDIDAT")
public class Candidat {
    @Id
    @Column(name = "NO_CANDIDAT", nullable = false, length = 50)
    private String noCandidat;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "CODE_FORMATION", referencedColumnName = "ANNEE_UNIVERSITAIRE", nullable = false),
            @JoinColumn(name = "ANNEE_UNIVERSITAIRE", referencedColumnName = "CODE_FORMATION", nullable = false)
    })
    private Promotion promotion;

    @Column(name = "NOM", nullable = false, length = 50)
    private String nom;

    @Column(name = "PRENOM", nullable = false, length = 50)
    private String prenom;

    @Column(name = "SEXE", nullable = false, length = 1)
    private String sexe;

    @Column(name = "DATE_NAISSANCE", nullable = false)
    private LocalDate dateNaissance;

    @Column(name = "LIEU_NAISSANCE", nullable = false)
    private String lieuNaissance;

    @Column(name = "NATIONALITE", nullable = false, length = 50)
    private String nationalite;

    @Column(name = "TELEPHONE", length = 20)
    private String telephone;

    @Column(name = "MOBILE", length = 20)
    private String mobile;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "ADRESSE", nullable = false)
    private String adresse;

    @Column(name = "CODE_POSTAL", length = 10)
    private String codePostal;

    @Column(name = "VILLE", nullable = false)
    private String ville;

    @Column(name = "PAYS_ORIGINE", nullable = false, length = 5)
    private String paysOrigine;

    @Column(name = "UNIVERSITE_ORIGINE", nullable = false, length = 6)
    private String universiteOrigine;

    @Column(name = "LISTE_SELECTION", length = 6)
    private String listeSelection;

    @Column(name = "SELECTION_NO_ORDRE")
    private Long selectionNoOrdre;

    @Column(name = "CONFIRMATION_CANDIDAT")
    private Boolean confirmationCandidat;

    @Column(name = "DATE_REPONSE_CANDIDAT")
    private LocalDate dateReponseCandidat;

}