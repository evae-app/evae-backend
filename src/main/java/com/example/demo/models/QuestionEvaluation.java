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
@Table(name = "QUESTION_EVALUATION")
public class QuestionEvaluation {
    @Id
    @Column(name = "ID_QUESTION_EVALUATION", nullable = false)
    private Integer id;

    @Column(name = "ID_RUBRIQUE_EVALUATION", nullable = false)
    private Integer idRubriqueEvaluation;

    @Column(name = "ID_QUESTION")
    private Integer idQuestion;

    @Column(name = "ID_QUALIFICATIF")
    private Integer idQualificatif;

    @Column(name = "ORDRE", nullable = false)
    private Short ordre;

    @Column(name = "INTITULE", length = 64)
    private String intitule;

}