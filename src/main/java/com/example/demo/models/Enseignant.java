package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "ENSEIGNANT", schema = "SPI")
public class Enseignant {
    @Id
    @SequenceGenerator(name = "ENSEIGNANT_id_gen", sequenceName = "ENS_SEQ", allocationSize = 1)
    @Column(name = "NO_ENSEIGNANT", nullable = false)
    private Short id;

    @Column(name = "\"TYPE\"", nullable = false, length = 5)
    private String type;

    @Column(name = "SEXE", nullable = false, length = 1)
    private String sexe;

    @Column(name = "NOM", nullable = false, length = 50)
    private String nom;

    @Column(name = "PRENOM", nullable = false, length = 50)
    private String prenom;

    @Column(name = "ADRESSE", nullable = false)
    private String adresse;

    @Column(name = "CODE_POSTAL", nullable = false, length = 10)
    private String codePostal;

    @Column(name = "VILLE", nullable = false)
    private String ville;

    @Column(name = "PAYS", nullable = false, length = 5)
    private String pays;

    @Column(name = "MOBILE", nullable = false, length = 20)
    private String mobile;

    @Column(name = "TELEPHONE", length = 20)
    private String telephone;

    @Column(name = "EMAIL_UBO", nullable = false)
    private String emailUbo;

    @Column(name = "EMAIL_PERSO")
    private String emailPerso;

    @OneToMany(mappedBy = "noEnseignant")
    private Set<Authentification> authentifications = new LinkedHashSet<>();

    @OneToMany(mappedBy = "noEnseignant")
    private Set<Droit> droits = new LinkedHashSet<>();

    @OneToMany(mappedBy = "noEnseignant")
    private Set<ElementConstitutif> elementConstitutifs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "noEnseignant")
    private Set<Evaluation> evaluations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "noEnseignant")
    private Set<Promotion> promotions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "noEnseignant")
    private Set<Question> questions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "noEnseignant")
    private Set<Rubrique> rubriques = new LinkedHashSet<>();

    @OneToMany(mappedBy = "noEnseignant")
    private Set<UniteEnseignement> uniteEnseignements = new LinkedHashSet<>();

	public Short getId() {
		return id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmailUbo() {
		return emailUbo;
	}

	public void setEmailUbo(String emailUbo) {
		this.emailUbo = emailUbo;
	}

	public String getEmailPerso() {
		return emailPerso;
	}

	public void setEmailPerso(String emailPerso) {
		this.emailPerso = emailPerso;
	}

	public Set<Authentification> getAuthentifications() {
		return authentifications;
	}

	public void setAuthentifications(Set<Authentification> authentifications) {
		this.authentifications = authentifications;
	}

	public Set<Droit> getDroits() {
		return droits;
	}

	public void setDroits(Set<Droit> droits) {
		this.droits = droits;
	}

	public Set<ElementConstitutif> getElementConstitutifs() {
		return elementConstitutifs;
	}

	public void setElementConstitutifs(Set<ElementConstitutif> elementConstitutifs) {
		this.elementConstitutifs = elementConstitutifs;
	}

	public Set<Evaluation> getEvaluations() {
		return evaluations;
	}

	public void setEvaluations(Set<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}

	public Set<Promotion> getPromotions() {
		return promotions;
	}

	public void setPromotions(Set<Promotion> promotions) {
		this.promotions = promotions;
	}

	public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	public Set<Rubrique> getRubriques() {
		return rubriques;
	}

	public void setRubriques(Set<Rubrique> rubriques) {
		this.rubriques = rubriques;
	}

	public Set<UniteEnseignement> getUniteEnseignements() {
		return uniteEnseignements;
	}

	public void setUniteEnseignements(Set<UniteEnseignement> uniteEnseignements) {
		this.uniteEnseignements = uniteEnseignements;
	}
    
    

}