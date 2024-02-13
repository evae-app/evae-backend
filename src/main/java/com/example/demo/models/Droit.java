package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "DROIT")
public class Droit {
    @EmbeddedId
    private DroitId id;

    @Column(name = "CONSULTATION", nullable = false)
    private Boolean consultation = false;

    @Column(name = "DUPLICATION", nullable = false)
    private Boolean duplication = false;

}