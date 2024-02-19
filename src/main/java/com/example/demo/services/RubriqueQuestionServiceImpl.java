package com.example.demo.services;

import com.example.demo.DTO.RubriqueQuestionDTO;
import com.example.demo.models.Question;
import com.example.demo.models.Rubrique;
import com.example.demo.models.RubriqueQuestion;
import com.example.demo.models.RubriqueQuestionId;
import com.example.demo.repositories.QuestionRepository;
import com.example.demo.repositories.RubriqueQuestionRepository;
import com.example.demo.repositories.RubriqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RubriqueQuestionServiceImpl implements RubriqueQuestionService{
    @Autowired
    private RubriqueQuestionRepository rubriqueQuestionRepository;
    @Autowired
    private RubriqueRepository rubriqueRepository;
    @Autowired
    private QuestionRepository questionRepository;
    /*@Override
    public RubriqueQuestion createRubriqueQuestion(RubriqueQuestion rubriqueQuestion) {
        return rubriqueQuestionRepository.save(rubriqueQuestion);
    }*/

    public RubriqueQuestion createRubriqueQuestion(RubriqueQuestionDTO rubriqueQuestionDTO) {
        RubriqueQuestion rubriqueQuestion = new RubriqueQuestion();
        RubriqueQuestionId rubriqueQuestionId = new RubriqueQuestionId();

        rubriqueQuestionId.setIdRubrique(rubriqueQuestionDTO.getIdRubrique());
        rubriqueQuestionId.setIdQuestion(rubriqueQuestionDTO.getIdQuestion());

        rubriqueQuestion.setId(rubriqueQuestionId);
        Rubrique rubrique = rubriqueRepository.findById(rubriqueQuestionDTO.getIdRubrique())
                .orElseThrow(() -> new RuntimeException("Rubrique not found"));
        rubriqueQuestion.setIdRubrique(rubrique);


        Question question = questionRepository.findById(rubriqueQuestionDTO.getIdQuestion())
                .orElseThrow(() -> new RuntimeException("Question not found"));
        rubriqueQuestion.setIdQuestion(question);

        rubriqueQuestion.setOrdre(rubriqueQuestionDTO.getOrdre().longValue());

        return rubriqueQuestionRepository.save(rubriqueQuestion);
    }

    public Set<Question> getQuestionsByRubrique(Rubrique rubrique) {
        return rubriqueQuestionRepository.findQuestionsByRubrique(rubrique);
    }


}
