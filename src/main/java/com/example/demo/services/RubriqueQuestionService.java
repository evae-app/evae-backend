package com.example.demo.services;

import com.example.demo.DTO.RubriqueQuestionDTO;
import com.example.demo.exception.RubriqueNotFoundException;
import com.example.demo.exception.RubriqueQuestionNotFoundException;
import com.example.demo.models.Question;
import com.example.demo.models.Rubrique;
import com.example.demo.models.RubriqueQuestion;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RubriqueQuestionService {
    public RubriqueQuestion createRubriqueQuestion(RubriqueQuestionDTO rubriqueQuestionDTO);
    public Set<Question> getQuestionsByRubrique(Rubrique rubrique);
    public List<RubriqueQuestionDTO> getAllRubriqueQuestion();



    public String deleteRubriqueQuestionsByRubriqueId(Integer rubriqueId) throws RubriqueNotFoundException;

    public String deleteRubriqueQuestionByIds(Integer rubriqueId, Integer questionId) throws RubriqueQuestionNotFoundException;
    void swapOrdre(Integer idRubrique1, Integer idQuestion1, Integer idRubrique2, Integer idQuestion2) throws RubriqueQuestionNotFoundException;




    //aprem
    public Map<Integer, List<RubriqueQuestionDTO>> getQuestionsGroupedByRubrique();

    public Map<Integer, List<RubriqueQuestionDTO>> getQuestionsGroupedByRubriqueOrderedByOrdre();



}
