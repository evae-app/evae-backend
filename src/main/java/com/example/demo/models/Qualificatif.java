package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "QUALIFICATIF", schema = "SPI")
public class Qualificatif {
    @Id
    @Column(name = "ID_QUALIFICATIF", nullable = false)
    private Integer id;

    @Column(name = "MAXIMAL", nullable = false, length = 16)
    private String maximal;

    @Column(name = "MINIMAL", nullable = false, length = 16)
    private String minimal;

    @OneToMany(mappedBy = "idQualificatif")
    private Set<Question> questions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idQualificatif")
    private Set<QuestionEvaluation> questionEvaluations = new LinkedHashSet<>();

}