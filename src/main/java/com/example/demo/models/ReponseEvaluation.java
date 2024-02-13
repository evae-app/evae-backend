package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "REPONSE_EVALUATION", schema = "SPI")
public class ReponseEvaluation {
    @Id
    @Column(name = "ID_REPONSE_EVALUATION", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_EVALUATION", nullable = false)
    private Evaluation idEvaluation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NO_ETUDIANT")
    private Etudiant noEtudiant;

    @Column(name = "COMMENTAIRE", length = 512)
    private String commentaire;

    @Column(name = "NOM", length = 32)
    private String nom;

    @Column(name = "PRENOM", length = 32)
    private String prenom;

}