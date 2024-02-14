package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "ENSEIGNANT", schema = "SPI")
public class Enseignant {
    @Id
    @SequenceGenerator(name = "ENSEIGNANT_id_gen", sequenceName = "ENS_SEQ", allocationSize = 1)
    @Column(name = "NO_ENSEIGNANT", nullable = false)
    private Short id;

    @Column(name = "\"TYPE\"", nullable = false, length = 5)
    private String type;

    @Column(name = "SEXE", nullable = false, length = 1)
    private String sexe;

    @Column(name = "NOM", nullable = false, length = 50)
    private String nom;

    @Column(name = "PRENOM", nullable = false, length = 50)
    private String prenom;

    @Column(name = "ADRESSE", nullable = false)
    private String adresse;

    @Column(name = "CODE_POSTAL", nullable = false, length = 10)
    private String codePostal;

    @Column(name = "VILLE", nullable = false)
    private String ville;

    @Column(name = "PAYS", nullable = false, length = 5)
    private String pays;

    @Column(name = "MOBILE", nullable = false, length = 20)
    private String mobile;

    @Column(name = "TELEPHONE", length = 20)
    private String telephone;

    @Column(name = "EMAIL_UBO", nullable = false)
    private String emailUbo;

    @Column(name = "EMAIL_PERSO")
    private String emailPerso;

    @OneToMany(mappedBy = "noEnseignant")
    private Set<Authentification> authentifications = new LinkedHashSet<>();

    @OneToMany(mappedBy = "noEnseignant")
    private Set<Droit> droits = new LinkedHashSet<>();

    @OneToMany(mappedBy = "noEnseignant")
    private Set<ElementConstitutif> elementConstitutifs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "noEnseignant")
    private Set<Evaluation> evaluations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "noEnseignant")
    private Set<Promotion> promotions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "noEnseignant")
    private Set<Question> questions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "noEnseignant")
    private Set<Rubrique> rubriques = new LinkedHashSet<>();

    @OneToMany(mappedBy = "noEnseignant")
    private Set<UniteEnseignement> uniteEnseignements = new LinkedHashSet<>();

}