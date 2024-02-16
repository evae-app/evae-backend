package com.example.demo.controllers;

import com.example.demo.DTO.RubriqueQuestionDTO;
import com.example.demo.models.Question;
import com.example.demo.models.Rubrique;
import com.example.demo.models.RubriqueQuestion;
import com.example.demo.repositories.RubriqueRepository;
import com.example.demo.services.RubriqueQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/rubriqueQuestions")
public class RubriqueQuestionController {

    @Autowired
    private RubriqueQuestionService rubriqueQuestionService;
    @Autowired
    private RubriqueRepository rubriqueRepository;

    @PostMapping
    public ResponseEntity<RubriqueQuestion> createRubriqueQuestion(@RequestBody RubriqueQuestionDTO rubriqueQuestionDTO) {
        RubriqueQuestion rubriqueQuestion = rubriqueQuestionService.createRubriqueQuestion(rubriqueQuestionDTO);
        return ResponseEntity.ok(rubriqueQuestion);
    }

    @GetMapping("/{rubriqueId}/questions")
    public ResponseEntity<Set<Question>> getQuestionsByRubrique(@PathVariable Integer rubriqueId) {
        Rubrique rubrique = rubriqueRepository.findById(rubriqueId)
                .orElseThrow(() -> new RuntimeException("Rubrique not found"));

        Set<Question> questions = rubriqueQuestionService.getQuestionsByRubrique(rubrique);
        return ResponseEntity.ok(questions);
    }
}
