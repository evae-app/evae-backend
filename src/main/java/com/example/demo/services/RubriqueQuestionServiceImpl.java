package com.example.demo.services;

import com.example.demo.DTO.QualificatifDTO;
import com.example.demo.DTO.QuestionDTO;
import com.example.demo.DTO.RubriqueDTO;
import com.example.demo.DTO.RubriqueQuestionDTO;
import com.example.demo.exception.DuplicateEntityException;
import com.example.demo.exception.OrdreException;
import com.example.demo.exception.RubriqueNotFoundException;
import com.example.demo.exception.RubriqueQuestionNotFoundException;
import com.example.demo.models.*;
import com.example.demo.repositories.QuestionRepository;
import com.example.demo.repositories.RubriqueQuestionRepository;
import com.example.demo.repositories.RubriqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.*;

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


    //aprem
    public Map<Integer, List<RubriqueQuestionDTO>> getQuestionsGroupedByRubrique() {
        List<RubriqueQuestion> rubriqueQuestions = rubriqueQuestionRepository.findAll();
        Map<Integer, List<RubriqueQuestionDTO>> groupedQuestions = new HashMap<>();

        for (RubriqueQuestion rubriqueQuestion : rubriqueQuestions) {
            RubriqueQuestionDTO dto = new RubriqueQuestionDTO();
            dto.setIdRubrique(rubriqueQuestion.getIdRubrique().getId());
            dto.setIdQuestion(rubriqueQuestion.getIdQuestion().getId());
            dto.setOrdre(rubriqueQuestion.getOrdre());

            // Create the QualificatifDTO associated
            Qualificatif qualificatif = rubriqueQuestion.getIdQuestion().getIdQualificatif();
            QualificatifDTO qualificatifDTO = new QualificatifDTO();
            qualificatifDTO.setId(qualificatif.getId());
            qualificatifDTO.setMaximal(qualificatif.getMaximal());
            qualificatifDTO.setMinimal(qualificatif.getMinimal());

            // Create the QuestionDTO
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

            // Group by idRubrique
            if (groupedQuestions.containsKey(rubrique.getId())) {
                groupedQuestions.get(rubrique.getId()).add(dto);
            } else {
                List<RubriqueQuestionDTO> list = new ArrayList<>();
                list.add(dto);
                groupedQuestions.put(rubrique.getId(), list);
            }
        }

        return groupedQuestions;
    }




    @Override
    public String deleteRubriqueQuestionsByRubriqueId(Integer rubriqueId) throws RubriqueNotFoundException {
        Optional<Rubrique> rubriqueOptional = rubriqueRepository.findById(rubriqueId);
        if (rubriqueOptional.isPresent()) {
            rubriqueQuestionRepository.deleteByRubriqueId(rubriqueId);
            return "Deletion successful.";
        } else {
            throw new RubriqueNotFoundException("Rubrique with ID " + rubriqueId + " not found.");
        }
    }


    @Override
    public String deleteRubriqueQuestionByIds(Integer rubriqueId, Integer questionId) throws RubriqueQuestionNotFoundException {
        Optional<RubriqueQuestion> rubriqueQuestionOptional = rubriqueQuestionRepository.findById(new RubriqueQuestionId(rubriqueId, questionId));
        if (rubriqueQuestionOptional.isPresent()) {
            rubriqueQuestionRepository.deleteById(new RubriqueQuestionId(rubriqueId, questionId));
            return "Deletion successful.";
        } else {
            throw new RubriqueQuestionNotFoundException("RubriqueQuestion with rubriqueId " + rubriqueId + " and questionId " + questionId + " not found.");
        }
    }


    //aprem2
    public Map<Integer, List<RubriqueQuestionDTO>> getQuestionsGroupedByRubriqueOrderedByOrdre() {
        List<RubriqueQuestion> rubriqueQuestions = rubriqueQuestionRepository.findAll();
        Map<Integer, List<RubriqueQuestionDTO>> groupedQuestions = new HashMap<>();

        // Sort rubriqueQuestions by ordre
        rubriqueQuestions.sort(Comparator.comparing(RubriqueQuestion::getOrdre));

        for (RubriqueQuestion rubriqueQuestion : rubriqueQuestions) {
            RubriqueQuestionDTO dto = new RubriqueQuestionDTO();
            dto.setIdRubrique(rubriqueQuestion.getIdRubrique().getId());
            dto.setIdQuestion(rubriqueQuestion.getIdQuestion().getId());
            dto.setOrdre(rubriqueQuestion.getOrdre());

            // Create the QualificatifDTO associated
            Qualificatif qualificatif = rubriqueQuestion.getIdQuestion().getIdQualificatif();
            QualificatifDTO qualificatifDTO = new QualificatifDTO();
            qualificatifDTO.setId(qualificatif.getId());
            qualificatifDTO.setMaximal(qualificatif.getMaximal());
            qualificatifDTO.setMinimal(qualificatif.getMinimal());

            // Create the QuestionDTO
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

            // Group by idRubrique
            if (groupedQuestions.containsKey(rubrique.getId())) {
                groupedQuestions.get(rubrique.getId()).add(dto);
            } else {
                List<RubriqueQuestionDTO> list = new ArrayList<>();
                list.add(dto);
                groupedQuestions.put(rubrique.getId(), list);
            }
        }

        return groupedQuestions;
    }



    @Override
    @Transactional
    public void swapOrdre(Integer idRubrique1, Integer idQuestion1, Integer idRubrique2, Integer idQuestion2) throws RubriqueQuestionNotFoundException {
        RubriqueQuestion rubriqueQuestion1 = rubriqueQuestionRepository.findById(new RubriqueQuestionId(idRubrique1, idQuestion1))
                .orElseThrow(() -> new RubriqueQuestionNotFoundException("RubriqueQuestion with idRubrique " + idRubrique1 + " and idQuestion " + idQuestion1 + " not found"));
        RubriqueQuestion rubriqueQuestion2 = rubriqueQuestionRepository.findById(new RubriqueQuestionId(idRubrique2, idQuestion2))
                .orElseThrow(() -> new RubriqueQuestionNotFoundException("RubriqueQuestion with idRubrique " + idRubrique2 + " and idQuestion " + idQuestion2 + " not found"));

        Long ordre1 = rubriqueQuestion1.getOrdre();
        Long ordre2 = rubriqueQuestion2.getOrdre();

        rubriqueQuestion1.setOrdre(ordre2);
        rubriqueQuestion2.setOrdre(ordre1);

        rubriqueQuestionRepository.save(rubriqueQuestion1);
        rubriqueQuestionRepository.save(rubriqueQuestion2);
    }



}
