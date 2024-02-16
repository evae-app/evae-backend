package com.example.demo.services;

import com.example.demo.DTO.RubriqueQuestionDTO;
import com.example.demo.models.Question;
import com.example.demo.models.Rubrique;
import com.example.demo.models.RubriqueQuestion;

import java.util.List;
import java.util.Set;

public interface RubriqueQuestionService {
    public RubriqueQuestion createRubriqueQuestion(RubriqueQuestionDTO rubriqueQuestionDTO);

    public Set<Question> getQuestionsByRubrique(Rubrique rubrique);
}
