package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "UNITE_ENSEIGNEMENT", schema = "SPI")
public class UniteEnseignement {
    @SequenceGenerator(name = "UNITE_ENSEIGNEMENT_id_gen", sequenceName = "AUT_SEQ", allocationSize = 1)
    @EmbeddedId
    private UniteEnseignementId id;

    @MapsId("codeFormation")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CODE_FORMATION", nullable = false)
    private Formation codeFormation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "NO_ENSEIGNANT", nullable = false)
    private Enseignant noEnseignant;

    @Column(name = "DESIGNATION", nullable = false, length = 64)
    private String designation;

    @Column(name = "SEMESTRE", nullable = false, length = 3)
    private String semestre;

    @Column(name = "DESCRIPTION", length = 256)
    private String description;

    @Column(name = "NBH_CM")
    private Long nbhCm;

    @Column(name = "NBH_TD")
    private Short nbhTd;

    @Column(name = "NBH_TP")
    private Short nbhTp;

    @OneToMany(mappedBy = "uniteEnseignement")
    private Set<ElementConstitutif> elementConstitutifs = new LinkedHashSet<>();

    /*@OneToMany(mappedBy = "uniteEnseignement")
    private Set<Evaluation> evaluations = new LinkedHashSet<>();*/

}