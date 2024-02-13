package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "PROMOTION", schema = "SPI")
public class Promotion {
    @EmbeddedId
    private PromotionId id;

    @MapsId("codeFormation")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CODE_FORMATION", nullable = false)
    private Formation codeFormation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NO_ENSEIGNANT")
    private Enseignant noEnseignant;

    @Column(name = "SIGLE_PROMOTION", length = 16)
    private String siglePromotion;

    @Column(name = "NB_MAX_ETUDIANT", nullable = false)
    private Short nbMaxEtudiant;

    @Column(name = "DATE_REPONSE_LP")
    private LocalDate dateReponseLp;

    @Column(name = "DATE_REPONSE_LALP")
    private LocalDate dateReponseLalp;

    @Column(name = "DATE_RENTREE")
    private LocalDate dateRentree;

    @Column(name = "LIEU_RENTREE", length = 12)
    private String lieuRentree;

    @Column(name = "PROCESSUS_STAGE", length = 5)
    private String processusStage;

    @Column(name = "COMMENTAIRE")
    private String commentaire;

}