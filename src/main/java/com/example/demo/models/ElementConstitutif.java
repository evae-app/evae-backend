package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "ELEMENT_CONSTITUTIF", schema = "SPI")
public class ElementConstitutif {
    @SequenceGenerator(name = "ELEMENT_CONSTITUTIF_id_gen", sequenceName = "AUT_SEQ", allocationSize = 1)
    @EmbeddedId
    private ElementConstitutifId id;

    @MapsId("id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "CODE_FORMATION", referencedColumnName = "CODE_FORMATION", nullable = false),
            @JoinColumn(name = "CODE_UE", referencedColumnName = "CODE_UE", nullable = false)
    })
    private UniteEnseignement uniteEnseignement;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "NO_ENSEIGNANT", nullable = false)
    private Enseignant noEnseignant;

    @Column(name = "DESIGNATION", nullable = false, length = 64)
    private String designation;

    @Column(name = "DESCRIPTION", length = 240)
    private String description;

    @Column(name = "NBH_CM")
    private Short nbhCm;

    @Column(name = "NBH_TD")
    private Short nbhTd;

    @Column(name = "NBH_TP")
    private Short nbhTp;

    @OneToMany(mappedBy = "elementConstitutif")
    private Set<Evaluation> evaluations = new LinkedHashSet<>();

}