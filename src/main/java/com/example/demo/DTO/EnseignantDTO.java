package com.example.demo.DTO;

import javax.persistence.Column;

public class EnseignantDTO {
	
	private Short id;

    private String emailUbo;
    
    private String nom;

    private String prenom;

	public Short getId() {
		return id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public String getEmailUbo() {
		return emailUbo;
	}

	public void setEmailUbo(String emailUbo) {
		this.emailUbo = emailUbo;
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
    
    

}
