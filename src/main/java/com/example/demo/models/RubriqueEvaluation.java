package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "RUBRIQUE_EVALUATION", schema = "SPI")
public class RubriqueEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RUBRIQUE_EVALUATION_id_gen")
    @SequenceGenerator(name = "RUBRIQUE_EVALUATION_id_gen", sequenceName = "REV_SEQ", allocationSize = 1)
    @Column(name = "ID_RUBRIQUE_EVALUATION", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_EVALUATION", nullable = false)
    private Evaluation idEvaluation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_RUBRIQUE")
    private Rubrique idRubrique;

    @Column(name = "ORDRE", nullable = false)
    private Short ordre;

    @Column(name = "DESIGNATION", length = 64)
    private String designation;

    @OneToMany(mappedBy = "idRubriqueEvaluation")
    private Set<QuestionEvaluation> questionEvaluations = new LinkedHashSet<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Evaluation getIdEvaluation() {
		return idEvaluation;
	}

	public void setIdEvaluation(Evaluation idEvaluation) {
		this.idEvaluation = idEvaluation;
	}

	public Rubrique getIdRubrique() {
		return idRubrique;
	}

	public void setIdRubrique(Rubrique idRubrique) {
		this.idRubrique = idRubrique;
	}

	public Short getOrdre() {
		return ordre;
	}

	public void setOrdre(Short ordre) {
		this.ordre = ordre;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Set<QuestionEvaluation> getQuestionEvaluations() {
		return questionEvaluations;
	}

	public void setQuestionEvaluations(Set<QuestionEvaluation> questionEvaluations) {
		this.questionEvaluations = questionEvaluations;
	}
    
    
    
}