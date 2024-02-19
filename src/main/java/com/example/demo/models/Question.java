package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "QUESTION", schema = "SPI")
@JsonIgnoreProperties({"rubriqueQuestions"})
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QUESTION_id_gen")
    @SequenceGenerator(name = "QUESTION_id_gen", sequenceName = "QUE_SEQ", allocationSize = 1)
    @Column(name = "ID_QUESTION", nullable = false)
    private Integer id;

    @Column(name = "\"TYPE\"", nullable = false, length = 10)
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NO_ENSEIGNANT")
    private Enseignant noEnseignant;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_QUALIFICATIF", nullable = false)
    @JsonIgnore
    private Qualificatif idQualificatif;

    @Column(name = "INTITULE", nullable = false, length = 64)
    private String intitule;

    @OneToMany(mappedBy = "idQuestion")
    private Set<QuestionEvaluation> questionEvaluations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idQuestion")
    @JsonBackReference
    private Set<RubriqueQuestion> rubriqueQuestions = new LinkedHashSet<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Enseignant getNoEnseignant() {
		return noEnseignant;
	}

	public void setNoEnseignant(Enseignant noEnseignant) {
		this.noEnseignant = noEnseignant;
	}

	public Qualificatif getIdQualificatif() {
		return idQualificatif;
	}

	public void setIdQualificatif(Qualificatif idQualificatif) {
		this.idQualificatif = idQualificatif;
	}

	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}

	public Set<QuestionEvaluation> getQuestionEvaluations() {
		return questionEvaluations;
	}

	public void setQuestionEvaluations(Set<QuestionEvaluation> questionEvaluations) {
		this.questionEvaluations = questionEvaluations;
	}

	public Set<RubriqueQuestion> getRubriqueQuestions() {
		return rubriqueQuestions;
	}

	public void setRubriqueQuestions(Set<RubriqueQuestion> rubriqueQuestions) {
		this.rubriqueQuestions = rubriqueQuestions;
	}
    
    


}