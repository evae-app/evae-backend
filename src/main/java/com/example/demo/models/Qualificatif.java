package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "QUALIFICATIF", schema = "SPI")
public class Qualificatif {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QUALIFICATIF_id_gen")
    @SequenceGenerator(name = "QUALIFICATIF_id_gen", sequenceName = "QUA_SEQ", allocationSize = 1)
    @Column(name = "ID_QUALIFICATIF", nullable = false)
    private Integer id;

    @Column(name = "MAXIMAL", nullable = false, length = 16)
    private String maximal;

    @Column(name = "MINIMAL", nullable = false, length = 16)
    private String minimal;

    @OneToMany(mappedBy = "idQualificatif")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Ignorer cette propriété lors de la désérialisation JSON
    private Set<Question> questions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idQualificatif")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Ignorer cette propriété lors de la désérialisation JSON
    private Set<QuestionEvaluation> questionEvaluations = new LinkedHashSet<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMaximal() {
		return maximal;
	}

	public void setMaximal(String maximal) {
		this.maximal = maximal;
	}

	public String getMinimal() {
		return minimal;
	}

	public void setMinimal(String minimal) {
		this.minimal = minimal;
	}

	public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	public Set<QuestionEvaluation> getQuestionEvaluations() {
		return questionEvaluations;
	}

	public void setQuestionEvaluations(Set<QuestionEvaluation> questionEvaluations) {
		this.questionEvaluations = questionEvaluations;
	}
    
    

}