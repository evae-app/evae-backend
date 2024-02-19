package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "RUBRIQUE_QUESTION", schema = "SPI")
public class RubriqueQuestion {
    @SequenceGenerator(name = "RUBRIQUE_QUESTION_id_gen", sequenceName = "AUT_SEQ", allocationSize = 1)
    @EmbeddedId
    private RubriqueQuestionId id;

    @MapsId("idRubrique")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_RUBRIQUE", nullable = false)
    private Rubrique idRubrique;

    @MapsId("idQuestion")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_QUESTION", nullable = false)
    private Question idQuestion;

    @Column(name = "ORDRE", nullable = false)
    private Long ordre;

	public RubriqueQuestionId getId() {
		return id;
	}

	public void setId(RubriqueQuestionId id) {
		this.id = id;
	}

	public Rubrique getIdRubrique() {
		return idRubrique;
	}

	public void setIdRubrique(Rubrique idRubrique) {
		this.idRubrique = idRubrique;
	}

	public Question getIdQuestion() {
		return idQuestion;
	}

	public void setIdQuestion(Question idQuestion) {
		this.idQuestion = idQuestion;
	}

	public Long getOrdre() {
		return ordre;
	}

	public void setOrdre(Long ordre) {
		this.ordre = ordre;
	}
    
    

}