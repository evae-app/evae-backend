package com.example.demo.DTO;

import java.time.Instant;
import java.time.LocalDate;

public class PromotionDTO {
	
	private String anneePro;
	private String siglePro;
    private Short nbEtuSouhaite;
    private String etatPreselection;
    private LocalDate dateRentree;
    private String lieuRentree;
    private Instant dateReponseLp;
    private String commentaire;
    private Instant dateReponseLalp;
    private String processusStage;
	public String getAnneePro() {
		return anneePro;
	}
	public void setAnneePro(String anneePro) {
		this.anneePro = anneePro;
	}
	public String getSiglePro() {
		return siglePro;
	}
	public void setSiglePro(String siglePro) {
		this.siglePro = siglePro;
	}
	public Short getNbEtuSouhaite() {
		return nbEtuSouhaite;
	}
	public void setNbEtuSouhaite(Short nbEtuSouhaite) {
		this.nbEtuSouhaite = nbEtuSouhaite;
	}
	public String getEtatPreselection() {
		return etatPreselection;
	}
	public void setEtatPreselection(String etatPreselection) {
		this.etatPreselection = etatPreselection;
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
	public Instant getDateReponseLp() {
		return dateReponseLp;
	}
	public void setDateReponseLp(Instant dateReponseLp) {
		this.dateReponseLp = dateReponseLp;
	}
	public String getCommentaire() {
		return commentaire;
	}
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	public Instant getDateReponseLalp() {
		return dateReponseLalp;
	}
	public void setDateReponseLalp(Instant dateReponseLalp) {
		this.dateReponseLalp = dateReponseLalp;
	}
	public String getProcessusStage() {
		return processusStage;
	}
	public void setProcessusStage(String processusStage) {
		this.processusStage = processusStage;
	}
    
    
}
