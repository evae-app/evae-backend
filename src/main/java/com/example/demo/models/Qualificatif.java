package com.example.demo.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMaximal() {
		return maximal;
	}

	public void setMaximal(String maximal) {
		this.maximal = maximal;
	}

	public String getMinimal() {
		return minimal;
	}

	public void setMinimal(String minimal) {
		this.minimal = minimal;
	}
    
    

}