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
public class ElementConstitutifId implements Serializable {
    private static final long serialVersionUID = -3536497401427526070L;
    @Column(name = "CODE_FORMATION", nullable = false, length = 8)
    private String codeFormation;

    @Column(name = "CODE_UE", nullable = false, length = 8)
    private String codeUe;

    @Column(name = "CODE_EC", nullable = false, length = 8)
    private String codeEc;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ElementConstitutifId entity = (ElementConstitutifId) o;
        return Objects.equals(this.codeEc, entity.codeEc) &&
                Objects.equals(this.codeUe, entity.codeUe) &&
                Objects.equals(this.codeFormation, entity.codeFormation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeEc, codeUe, codeFormation);
    }

	public String getCodeFormation() {
		return codeFormation;
	}

	public void setCodeFormation(String codeFormation) {
		this.codeFormation = codeFormation;
	}

	public String getCodeUe() {
		return codeUe;
	}

	public void setCodeUe(String codeUe) {
		this.codeUe = codeUe;
	}

	public String getCodeEc() {
		return codeEc;
	}

	public void setCodeEc(String codeEc) {
		this.codeEc = codeEc;
	}
    
    

}