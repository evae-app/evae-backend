package com.example.demo.controllers;

import com.example.demo.DTO.RubriqueQuestionDTO;
import com.example.demo.exception.RubriqueNotFoundException;
import com.example.demo.exception.RubriqueQuestionNotFoundException;
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
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/rubriqueQuestions")
public class RubriqueQuestionController {

    @Autowired
    private RubriqueQuestionService rubriqueQuestionService;
    @Autowired
    private RubriqueRepository rubriqueRepository;


    @GetMapping("/getAll")
    public ResponseEntity<List<RubriqueQuestionDTO>> getAllRubriqueQuestion() {
        List<RubriqueQuestionDTO> rubriqueQuestionDTOs = rubriqueQuestionService.getAllRubriqueQuestion();
        return new ResponseEntity<>(rubriqueQuestionDTOs, HttpStatus.OK);
    }

    //aprem
    @GetMapping("/groupedByRubrique")
    public ResponseEntity<Map<Integer, List<RubriqueQuestionDTO>>> getQuestionsGroupedByRubrique() {
        Map<Integer, List<RubriqueQuestionDTO>> groupedQuestions = rubriqueQuestionService.getQuestionsGroupedByRubrique();
        return new ResponseEntity<>(groupedQuestions, HttpStatus.OK);
    }

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




    @DeleteMapping("/{rubriqueId}")
    public ResponseEntity<?> deleteRubriqueQuestionsByRubriqueId(@PathVariable Integer rubriqueId) {
        try {
            rubriqueQuestionService.deleteRubriqueQuestionsByRubriqueId(rubriqueId);
            return ResponseEntity.ok("Deletion successful.");
        } catch (RubriqueNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @DeleteMapping("/{rubriqueId}/{questionId}")
    public ResponseEntity<?> deleteRubriqueQuestionByIds(@PathVariable Integer rubriqueId, @PathVariable Integer questionId) {
        try {
            rubriqueQuestionService.deleteRubriqueQuestionByIds(rubriqueId, questionId);
            return ResponseEntity.ok("Deletion successful.");
        } catch (RubriqueQuestionNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



    @PutMapping("/{rubriqueId1}/{questionId1}/{rubriqueId2}/{questionId2}/swapOrdre")
    public ResponseEntity<?> swapOrdre(@PathVariable Integer rubriqueId1,
                                       @PathVariable Integer questionId1,
                                       @PathVariable Integer rubriqueId2,
                                       @PathVariable Integer questionId2) {
        try {
            if (!rubriqueId1.equals(rubriqueId2)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("idRubrique1 and idRubrique2 must be equal.");
            }

            rubriqueQuestionService.swapOrdre(rubriqueId1, questionId1, rubriqueId2, questionId2);
            return ResponseEntity.ok("Ordre values swapped successfully.");
        } catch (RubriqueQuestionNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    //aprem2
    @GetMapping("/groupedByRubriqueOrderedByOrdre")
    public ResponseEntity<Map<Integer, List<RubriqueQuestionDTO>>> getQuestionsGroupedByRubriqueOrderedByOrdre() {
        Map<Integer, List<RubriqueQuestionDTO>> groupedQuestions = rubriqueQuestionService.getQuestionsGroupedByRubriqueOrderedByOrdre();
        return new ResponseEntity<>(groupedQuestions, HttpStatus.OK);
    }
}
