package com.example.demo.services;

import com.example.demo.DTO.QualificatifDTO;
import com.example.demo.DTO.QuestionDTO;
import com.example.demo.DTO.RubriqueDTO;
import com.example.demo.DTO.RubriqueQuestionDTO;
import com.example.demo.exception.DuplicateEntityException;
import com.example.demo.exception.OrdreException;
import com.example.demo.models.*;
import com.example.demo.repositories.QuestionRepository;
import com.example.demo.repositories.RubriqueQuestionRepository;
import com.example.demo.repositories.RubriqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
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

    public RubriqueQuestion createRubriqueQuestion(RubriqueQuestionDTO rubriqueQuestionDTO) {

        if (rubriqueQuestionRepository.existsById_IdRubriqueAndId_IdQuestion(
                rubriqueQuestionDTO.getIdRubrique(),
                rubriqueQuestionDTO.getIdQuestion())) {
            throw new DuplicateEntityException("cette rubrique existe déjà");
        }

        if (rubriqueQuestionRepository.existsById_IdRubriqueAndOrdre(
                rubriqueQuestionDTO.getIdRubrique(),
                rubriqueQuestionDTO.getOrdre())){
            throw new OrdreException("Une question de meme odre existe déjà");
        }

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

            rubriqueQuestion.setOrdre(rubriqueQuestionDTO.getOrdre());
            return rubriqueQuestionRepository.save(rubriqueQuestion);
    }

    public Set<Question> getQuestionsByRubrique(Rubrique rubrique) {
        return rubriqueQuestionRepository.findQuestionsByRubrique(rubrique);
    }

    public List<RubriqueQuestionDTO> getAllRubriqueQuestion() {
        List<RubriqueQuestion> rubriqueQuestions = rubriqueQuestionRepository.findAll();
        List<RubriqueQuestionDTO> rubriqueQuestionDTOs = new ArrayList<>();

        for (RubriqueQuestion rubriqueQuestion : rubriqueQuestions) {
            RubriqueQuestionDTO dto = new RubriqueQuestionDTO();
            dto.setIdRubrique(rubriqueQuestion.getIdRubrique().getId());
            dto.setIdQuestion(rubriqueQuestion.getIdQuestion().getId());
            dto.setOrdre(rubriqueQuestion.getOrdre());

            // Créez le QualificatifDTO associé
            Qualificatif qualificatif = rubriqueQuestion.getIdQuestion().getIdQualificatif();
            QualificatifDTO qualificatifDTO = new QualificatifDTO();
            qualificatifDTO.setId(qualificatif.getId());
            qualificatifDTO.setMaximal(qualificatif.getMaximal());
            qualificatifDTO.setMinimal(qualificatif.getMinimal());

            // Créez le QuestionDTO
            Question question = rubriqueQuestion.getIdQuestion();
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setId(question.getId());
            questionDTO.setType(question.getType());
            questionDTO.setIntitule(question.getIntitule());
            questionDTO.setIdQualificatif(qualificatifDTO);

            dto.setQuestionDTO(questionDTO);

            Rubrique rubrique = rubriqueQuestion.getIdRubrique();
            RubriqueDTO rubriqueDTO = new RubriqueDTO();
            rubriqueDTO.setId(rubrique.getId());
            rubriqueDTO.setType(rubrique.getType());
            rubriqueDTO.setDesignation(rubrique.getDesignation());
            rubriqueDTO.setOrdre(rubrique.getOrdre());

            dto.setRubriqueDTO(rubriqueDTO);

            rubriqueQuestionDTOs.add(dto);
        }

        return rubriqueQuestionDTOs;
    }

}
