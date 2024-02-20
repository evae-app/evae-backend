package com.example.demo.DTO;


import com.example.demo.models.Enseignant;
import com.example.demo.models.Etudiant;

public class UserDTO {
    private Long id;

    private String role;

    private String loginConnection;

    private String pseudoConnection;

    private String motPasse;

    private EnseignantDTO noEnseignant;

    private EtudiantDTO noEtudiant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLoginConnection() {
        return loginConnection;
    }

    public void setLoginConnection(String loginConnection) {
        this.loginConnection = loginConnection;
    }

    public String getPseudoConnection() {
        return pseudoConnection;
    }

    public void setPseudoConnection(String pseudoConnection) {
        this.pseudoConnection = pseudoConnection;
    }

    public String getMotPasse() {
        return motPasse;
    }

    public void setMotPasse(String motPasse) {
        this.motPasse = motPasse;
    }

    public EnseignantDTO getNoEnseignant() {
        return noEnseignant;
    }

    public void setNoEnseignant(EnseignantDTO noEnseignant) {
        this.noEnseignant = noEnseignant;
    }

    public EtudiantDTO getNoEtudiant() {
        return noEtudiant;
    }

    public void setNoEtudiant(EtudiantDTO noEtudiant) {
        this.noEtudiant = noEtudiant;
    }




}