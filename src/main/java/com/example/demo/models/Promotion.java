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
@Table(name = "PROMOTION", schema = "SPI")
public class Promotion {
    @SequenceGenerator(name = "PROMOTION_id_gen", sequenceName = "AUT_SEQ", allocationSize = 1)
    @EmbeddedId
    private PromotionId id;

    @MapsId("codeFormation")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CODE_FORMATION", nullable = false)
    private Formation codeFormation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NO_ENSEIGNANT")
    private Enseignant noEnseignant;

    @Column(name = "SIGLE_PROMOTION", length = 16)
    private String siglePromotion;

    @Column(name = "NB_MAX_ETUDIANT", nullable = false)
    private Short nbMaxEtudiant;

    @Column(name = "DATE_REPONSE_LP")
    private LocalDate dateReponseLp;

    @Column(name = "DATE_REPONSE_LALP")
    private LocalDate dateReponseLalp;

    @Column(name = "DATE_RENTREE")
    private LocalDate dateRentree;

    @Column(name = "LIEU_RENTREE", length = 12)
    private String lieuRentree;

    @Column(name = "PROCESSUS_STAGE", length = 5)
    private String processusStage;

    @Column(name = "COMMENTAIRE")
    private String commentaire;

    @OneToMany(mappedBy = "promotion")
    private Set<Candidat> candidats = new LinkedHashSet<>();

    @OneToMany(mappedBy = "promotion")
    private Set<Etudiant> etudiants = new LinkedHashSet<>();

    @OneToMany(mappedBy = "promotion")
    private Set<Evaluation> evaluations = new LinkedHashSet<>();

	public PromotionId getId() {
		return id;
	}

	public void setId(PromotionId id) {
		this.id = id;
	}

	public Formation getCodeFormation() {
		return codeFormation;
	}

	public void setCodeFormation(Formation codeFormation) {
		this.codeFormation = codeFormation;
	}

	public Enseignant getNoEnseignant() {
		return noEnseignant;
	}

	public void setNoEnseignant(Enseignant noEnseignant) {
		this.noEnseignant = noEnseignant;
	}

	public String getSiglePromotion() {
		return siglePromotion;
	}

	public void setSiglePromotion(String siglePromotion) {
		this.siglePromotion = siglePromotion;
	}

	public Short getNbMaxEtudiant() {
		return nbMaxEtudiant;
	}

	public void setNbMaxEtudiant(Short nbMaxEtudiant) {
		this.nbMaxEtudiant = nbMaxEtudiant;
	}

	public LocalDate getDateReponseLp() {
		return dateReponseLp;
	}

	public void setDateReponseLp(LocalDate dateReponseLp) {
		this.dateReponseLp = dateReponseLp;
	}

	public LocalDate getDateReponseLalp() {
		return dateReponseLalp;
	}

	public void setDateReponseLalp(LocalDate dateReponseLalp) {
		this.dateReponseLalp = dateReponseLalp;
	}

	public LocalDate getDateRentree() {
		return dateRentree;
	}

	public void setDateRentree(LocalDate dateRentree) {
		this.dateRentree = dateRentree;
	}

	public String getLieuRentree() {
		return lieuRentree;
	}

	public void setLieuRentree(String lieuRentree) {
		this.lieuRentree = lieuRentree;
	}

	public String getProcessusStage() {
		return processusStage;
	}

	public void setProcessusStage(String processusStage) {
		this.processusStage = processusStage;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public Set<Candidat> getCandidats() {
		return candidats;
	}

	public void setCandidats(Set<Candidat> candidats) {
		this.candidats = candidats;
	}

	public Set<Etudiant> getEtudiants() {
		return etudiants;
	}

	public void setEtudiants(Set<Etudiant> etudiants) {
		this.etudiants = etudiants;
	}

	public Set<Evaluation> getEvaluations() {
		return evaluations;
	}

	public void setEvaluations(Set<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}
    
    

}