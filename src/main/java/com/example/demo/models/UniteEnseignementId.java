package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class UniteEnseignementId implements Serializable {
    private static final long serialVersionUID = 7161939351384045919L;
    @Column(name = "CODE_FORMATION", nullable = false, length = 8)
    private String codeFormation;

    @Column(name = "CODE_UE", nullable = false, length = 8)
    private String codeUe;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UniteEnseignementId entity = (UniteEnseignementId) o;
        return Objects.equals(this.codeUe, entity.codeUe) &&
                Objects.equals(this.codeFormation, entity.codeFormation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeUe, codeFormation);
    }

}