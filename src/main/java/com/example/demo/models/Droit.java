package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "DROIT", schema = "SPI")
public class Droit {
    @SequenceGenerator(name = "DROIT_id_gen", sequenceName = "AUT_SEQ", allocationSize = 1)
    @EmbeddedId
    private DroitId id;

    @MapsId("idEvaluation")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_EVALUATION", nullable = false)
    private Evaluation idEvaluation;

    @MapsId("noEnseignant")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "NO_ENSEIGNANT", nullable = false)
    private Enseignant noEnseignant;

    @Column(name = "CONSULTATION", nullable = false)
    private Boolean consultation = false;

    @Column(name = "DUPLICATION", nullable = false)
    private Boolean duplication = false;

}