package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "EVALUATION", schema = "SPI")
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EVALUATION_id_gen")
    @SequenceGenerator(name = "EVALUATION_id_gen", sequenceName = "EVE_SEQ", allocationSize = 1)
    @Column(name = "ID_EVALUATION", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "NO_ENSEIGNANT", nullable = false)
    private Enseignant noEnseignant;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "CODE_FORMATION", referencedColumnName = "CODE_FORMATION", nullable = false),
            @JoinColumn(name = "CODE_UE", referencedColumnName = "CODE_UE", nullable = false),
            @JoinColumn(name = "CODE_EC", referencedColumnName = "CODE_EC", nullable = false)
    })
    private ElementConstitutif elementConstitutif;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "CODE_FORMATION", referencedColumnName = "CODE_FORMATION", insertable = false , updatable = false ),
            @JoinColumn(name = "ANNEE_UNIVERSITAIRE", referencedColumnName = "ANNEE_UNIVERSITAIRE", insertable = false , updatable = false )
    })
    private Promotion promotion;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumns({
			@JoinColumn(name = "CODE_FORMATION", referencedColumnName = "CODE_FORMATION", insertable=false, updatable=false),
			@JoinColumn(name = "CODE_UE", referencedColumnName = "CODE_UE",  insertable=false, updatable=false)
	})
	private UniteEnseignement uniteEnseignement;

    @Column(name = "NO_EVALUATION", nullable = false)
    private Short noEvaluation;

    @Column(name = "DESIGNATION", nullable = false, length = 16)
    private String designation;

    @Column(name = "ETAT", nullable = false, length = 3)
    private String etat;

    @Column(name = "PERIODE", length = 64)
    private String periode;

    @Column(name = "DEBUT_REPONSE", nullable = false)
    private LocalDate debutReponse;

    @Column(name = "FIN_REPONSE", nullable = false)
    private LocalDate finReponse;

    @OneToMany(mappedBy = "idEvaluation")
    private Set<Droit> droits = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idEvaluation")
    private Set<ReponseEvaluation> reponseEvaluations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idEvaluation")
    private Set<RubriqueEvaluation> rubriqueEvaluations = new LinkedHashSet<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Enseignant getNoEnseignant() {
		return noEnseignant;
	}

	public void setNoEnseignant(Enseignant noEnseignant) {
		this.noEnseignant = noEnseignant;
	}

	public ElementConstitutif getElementConstitutif() {
		return elementConstitutif;
	}

	public void setElementConstitutif(ElementConstitutif elementConstitutif) {
		this.elementConstitutif = elementConstitutif;
	}

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
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

	public Set<Droit> getDroits() {
		return droits;
	}

	public void setDroits(Set<Droit> droits) {
		this.droits = droits;
	}

	public Set<ReponseEvaluation> getReponseEvaluations() {
		return reponseEvaluations;
	}

	public void setReponseEvaluations(Set<ReponseEvaluation> reponseEvaluations) {
		this.reponseEvaluations = reponseEvaluations;
	}

	public Set<RubriqueEvaluation> getRubriqueEvaluations() {
		return rubriqueEvaluations;
	}

	public void setRubriqueEvaluations(Set<RubriqueEvaluation> rubriqueEvaluations) {
		this.rubriqueEvaluations = rubriqueEvaluations;
	}
    
    

}