package com.example.demo.models;


import javax.persistence.*;


@Entity
@Table(name = "QUESTION", schema = "SPI")
public class Question {
    @Id
    @Column(name = "ID_QUESTION", nullable = false)
    private Integer id;

    @Column(name = "\"TYPE\"", nullable = false, length = 10)
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NO_ENSEIGNANT")
    private Enseignant noEnseignant;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_QUALIFICATIF", nullable = false)
    private Qualificatif idQualificatif;

    @Column(name = "INTITULE", nullable = false, length = 64)
    private String intitule;

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
    
    
}