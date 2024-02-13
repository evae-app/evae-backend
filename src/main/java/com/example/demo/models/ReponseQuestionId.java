package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class ReponseQuestionId implements Serializable {
    @Serial
    private static final long serialVersionUID = -7482983767424851443L;
    @Column(name = "ID_REPONSE_EVALUATION", nullable = false)
    private Integer idReponseEvaluation;

    @Column(name = "ID_QUESTION_EVALUATION", nullable = false)
    private Integer idQuestionEvaluation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ReponseQuestionId entity = (ReponseQuestionId) o;
        return Objects.equals(this.idReponseEvaluation, entity.idReponseEvaluation) &&
                Objects.equals(this.idQuestionEvaluation, entity.idQuestionEvaluation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReponseEvaluation, idQuestionEvaluation);
    }

}