package com.example.demo.DTO;

import java.time.LocalDate;
import java.util.List;


import com.example.demo.models.ElementConstitutif;
import com.example.demo.models.Enseignant;
import com.example.demo.models.Promotion;

public class EvaluationDTO {
	
	private Integer id;

    private EnseignantDTO noEnseignant;
    
    private String codeFormation;
    
    private String codeUE;
    
    private String codeEC;

    private String promotion;

    private Short noEvaluation;

    private String designation;

    private String etat;

    private String periode;

    private LocalDate debutReponse;

    private LocalDate finReponse;
    
    List<EvaluationQuestionDTO> rubriques ;
    
    

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Short getNoEvaluation() {
		return noEvaluation;
	}

	public void setNoEvaluation(Short noEvaluation) {
		this.noEvaluation = noEvaluation;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public String getPeriode() {
		return periode;
	}

	public void setPeriode(String periode) {
		this.periode = periode;
	}

	public LocalDate getDebutReponse() {
		return debutReponse;
	}

	public void setDebutReponse(LocalDate debutReponse) {
		this.debutReponse = debutReponse;
	}

	public LocalDate getFinReponse() {
		return finReponse;
	}

	public void setFinReponse(LocalDate finReponse) {
		this.finReponse = finReponse;
	}

	public List<EvaluationQuestionDTO> getRubriques() {
		return rubriques;
	}

	public void setRubriques(List<EvaluationQuestionDTO> rubriques) {
		this.rubriques = rubriques;
	}

	public EnseignantDTO getNoEnseignant() {
		return noEnseignant;
	}

	public void setNoEnseignant(EnseignantDTO noEnseignant) {
		this.noEnseignant = noEnseignant;
	}

	public String getPromotion() {
		return promotion;
	}

	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}

	public String getCodeFormation() {
		return codeFormation;
	}

	public void setCodeFormation(String codeFormation) {
		this.codeFormation = codeFormation;
	}

	public String getCodeUE() {
		return codeUE;
	}

	public void setCodeUE(String codeUE) {
		this.codeUE = codeUE;
	}

	public String getCodeEC() {
		return codeEC;
	}

	public void setCodeEC(String codeEC) {
		this.codeEC = codeEC;
	}

	
	
    

}
