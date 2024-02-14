package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "CG_REF_CODES", schema = "SPI")
public class CgRefCode {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CG_REF_CODES_id_gen")
    @SequenceGenerator(name = "CG_REF_CODES_id_gen", sequenceName = "CGRC_SEQ", allocationSize = 1)
    @Column(name = "ID_CGRC", nullable = false)
    private Long id;

    @Column(name = "RV_DOMAIN", nullable = false, length = 100)
    private String rvDomain;

    @Column(name = "RV_LOW_VALUE", nullable = false, length = 240)
    private String rvLowValue;

    @Column(name = "RV_HIGH_VALUE", length = 240)
    private String rvHighValue;

    @Column(name = "RV_ABBREVIATION", length = 240)
    private String rvAbbreviation;

    @Column(name = "RV_MEANING", length = 240)
    private String rvMeaning;

}