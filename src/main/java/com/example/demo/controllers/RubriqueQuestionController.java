package com.example.demo.controllers;

import com.example.demo.models.RubriqueQuestion;
import com.example.demo.services.RubriqueQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rubriqueQuestions")
public class RubriqueQuestionController {

    @Autowired
    private RubriqueQuestionService rubriqueQuestionService;

    @PostMapping("/create")
    public ResponseEntity<RubriqueQuestion> createRubriqueQuestion(@RequestBody RubriqueQuestion rubriqueQuestion) {
        RubriqueQuestion createdRubriqueQuestion = rubriqueQuestionService.createRubriqueQuestion(rubriqueQuestion);
        return new ResponseEntity<>(createdRubriqueQuestion, HttpStatus.CREATED);
    }

}
