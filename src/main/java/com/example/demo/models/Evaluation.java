package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "EVALUATION", schema = "SPI")
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EVALUATION_id_gen")
    @SequenceGenerator(name = "EVALUATION_id_gen", sequenceName = "EVE_SEQ", allocationSize = 1)
    @Column(name = "ID_EVALUATION", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "NO_ENSEIGNANT", nullable = false)
    private Enseignant noEnseignant;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "CODE_FORMATION", referencedColumnName = "CODE_FORMATION", nullable = false),
            @JoinColumn(name = "CODE_UE", referencedColumnName = "CODE_UE", nullable = false),
            @JoinColumn(name = "CODE_EC", referencedColumnName = "CODE_EC", nullable = false)
    })
    private ElementConstitutif elementConstitutif;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "CODE_FORMATION", referencedColumnName = "CODE_FORMATION", insertable=false, updatable=false),
            @JoinColumn(name = "CODE_UE", referencedColumnName = "CODE_UE",  insertable=false, updatable=false)
    })
    private UniteEnseignement uniteEnseignement;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "CODE_FORMATION", referencedColumnName = "CODE_FORMATION", insertable = false , updatable = false ),
            @JoinColumn(name = "ANNEE_UNIVERSITAIRE", referencedColumnName = "ANNEE_UNIVERSITAIRE", insertable = false , updatable = false )
    })
    private Promotion promotion;

    @Column(name = "NO_EVALUATION", nullable = false)
    private Short noEvaluation;

    @Column(name = "DESIGNATION", nullable = false, length = 16)
    private String designation;

    @Column(name = "ETAT", nullable = false, length = 3)
    private String etat;

    @Column(name = "PERIODE", length = 64)
    private String periode;

    @Column(name = "DEBUT_REPONSE", nullable = false)
    private LocalDate debutReponse;

    @Column(name = "FIN_REPONSE", nullable = false)
    private LocalDate finReponse;

    @OneToMany(mappedBy = "idEvaluation")
    private Set<Droit> droits = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idEvaluation")
    private Set<ReponseEvaluation> reponseEvaluations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idEvaluation")
    private Set<RubriqueEvaluation> rubriqueEvaluations = new LinkedHashSet<>();

}