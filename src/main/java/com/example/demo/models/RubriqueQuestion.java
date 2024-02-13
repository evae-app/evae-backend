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
@Table(name = "RUBRIQUE_QUESTION")
public class RubriqueQuestion {
    @EmbeddedId
    private RubriqueQuestionId id;

    @Column(name = "ORDRE", nullable = false)
    private Long ordre;

}