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
@Table(name = "REPONSE_QUESTION")
public class ReponseQuestion {
    @EmbeddedId
    private ReponseQuestionId id;

    @Column(name = "POSITIONNEMENT")
    private Long positionnement;

}