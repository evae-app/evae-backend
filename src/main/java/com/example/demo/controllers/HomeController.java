package com.example.demo.controllers;

import com.example.demo.DTO.HomeInsightsDTO;
import com.example.demo.repositories.QualificatifRepository;
import com.example.demo.repositories.QuestionRepository;
import com.example.demo.repositories.RubriqueQuestionRepository;
import com.example.demo.repositories.RubriqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {
    @Autowired
    QualificatifRepository qualificatifRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    RubriqueRepository rubriqueRepository;

    @Autowired
    RubriqueQuestionRepository rubriqueQuestionRepository;

    @GetMapping
    public ResponseEntity<HomeInsightsDTO> getHomeInsights() {
        try {
            HomeInsightsDTO homeInsight = new HomeInsightsDTO();

            homeInsight.setNbrQualificatifs(qualificatifRepository.findAll().size());
            homeInsight.setNbrQuestions(questionRepository.findAll().size());
            homeInsight.setNbrRubriques(rubriqueRepository.findAll().size());
            homeInsight.setNbrRubriquesComposes(rubriqueQuestionRepository.findAll().size());

            return new ResponseEntity<>(homeInsight, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new HomeInsightsDTO(), HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
