package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "REPONSE_EVALUATION")
public class ReponseEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REPONSE_EVALUATION_id_gen")
    @SequenceGenerator(name = "REPONSE_EVALUATION_id_gen", sequenceName = "RPE_SEQ", allocationSize = 1)
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

    @OneToMany(mappedBy = "idReponseEvaluation")
    private Set<ReponseQuestion> reponseQuestions = new LinkedHashSet<>();

}