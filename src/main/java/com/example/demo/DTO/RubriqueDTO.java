package com.example.demo.DTO;

import com.example.demo.models.Enseignant;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;

@Setter
@Getter
public class RubriqueDTO {
    Integer id;
    private String type;
    private String designation;
    private Long ordre;
    //private List<Long> questionIds;
}
