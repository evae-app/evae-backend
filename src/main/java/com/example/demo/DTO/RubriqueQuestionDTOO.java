package com.example.demo.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class RubriqueQuestionDTOO {

    private RubriqueDTO RUBRIQUE;
    private List<QuestionDTO> questions ;

    public void addQuestion(QuestionDTO question) {
        if (questions == null) {
            questions = new ArrayList<>();
        }
        questions.add(question);
    }

}