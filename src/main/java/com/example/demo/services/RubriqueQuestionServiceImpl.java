package com.example.demo.services;

import com.example.demo.models.RubriqueQuestion;
import com.example.demo.repositories.RubriqueQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RubriqueQuestionServiceImpl implements RubriqueQuestionService {
    @Autowired
    private RubriqueQuestionRepository rubriqueQuestionRepository;

    @Override
    public RubriqueQuestion createRubriqueQuestion(RubriqueQuestion rubriqueQuestion) {
        return rubriqueQuestionRepository.save(rubriqueQuestion);
    }

}
