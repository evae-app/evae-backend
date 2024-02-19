package com.example.demo.DTO;

import java.util.List;

import com.example.demo.models.Question;
import com.example.demo.models.Rubrique;
import com.example.demo.models.RubriqueQuestionId;
import lombok.Getter;
import lombok.Setter;

@Getter

@Setter
public class RubriqueQuestionDTO {
    private Integer idRubrique;
    private Integer idQuestion;
    private Short ordre;
    private String Designation;
    private String type;
    
    List<QuestionDTO> questions;


	public void setType(String type) {
		this.type = type;
	}

	public void setDesignation(String designation) {
		Designation = designation;
	}

	public void setIdRubrique(Integer idRubrique) {
		this.idRubrique = idRubrique;
	}

	public void setIdQuestion(Integer idQuestion) {
		this.idQuestion = idQuestion;
	}

	public void setOrdre(Short ordre) {
		this.ordre = ordre;
	}

	public void setQuestions(List<QuestionDTO> questions) {
		this.questions = questions;
	}

    
    
}
