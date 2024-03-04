package com.example.demo.services;

import com.example.demo.DTO.*;
import com.example.demo.exception.*;
import com.example.demo.models.*;
import com.example.demo.repositories.QuestionRepository;
import com.example.demo.repositories.RubriqueEvaluationRepository;
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

    @Autowired
    private RubriqueEvaluationRepository rubriqueEvaluationRepository ;


//USED
    @Override
    public List<RubriqueQuestionDTOO> getAll() {
        List<RubriqueQuestion> rubquestions = rubriqueQuestionRepository.findAll();
        Map<Integer, RubriqueQuestionDTOO> rubriqueMap = new HashMap<>();

        for (RubriqueQuestion rubriquequestion : rubquestions) {
            RubriqueDTO rub = new RubriqueDTO();
            rub.setId(rubriquequestion.getIdRubrique().getId());
            rub.setType(rubriquequestion.getIdRubrique().getType());
            rub.setDesignation(rubriquequestion.getIdRubrique().getDesignation());
            rub.setOrdre(rubriquequestion.getIdRubrique().getOrdre());

            RubriqueQuestionDTOO rubriqueQuestionDTOO = rubriqueMap.getOrDefault(rub.getId(), new RubriqueQuestionDTOO());
            rubriqueQuestionDTOO.setRUBRIQUE(rub);

            QuestionDTO q = new QuestionDTO();
            q.setId(rubriquequestion.getIdQuestion().getId());
            q.setOrdre(rubriquequestion.getOrdre());
            q.setType(rubriquequestion.getIdQuestion().getType());
            q.setIntitule(rubriquequestion.getIdQuestion().getIntitule());
            QualificatifDTO qua = new QualificatifDTO();
            qua.setId(rubriquequestion.getIdQuestion().getIdQualificatif().getId());
            qua.setMaximal(rubriquequestion.getIdQuestion().getIdQualificatif().getMaximal());
            qua.setMinimal(rubriquequestion.getIdQuestion().getIdQualificatif().getMinimal());
            q.setIdQualificatif(qua);

            rubriqueQuestionDTOO.addQuestion(q);
            rubriqueMap.put(rub.getId(), rubriqueQuestionDTOO);
        }

        return new ArrayList<>(rubriqueMap.values());
    }

    //USED
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


    // USED
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

    //USED
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
                    .orElseThrow(() -> new NotFoundEntityException("rubrique"));
            rubriqueQuestion.setIdRubrique(rubrique);

            Question question = questionRepository.findById(rubriqueQuestionDTO.getIdQuestion())
                    .orElseThrow(() -> new NotFoundEntityException("question"));
            rubriqueQuestion.setIdQuestion(question);

            rubriqueQuestion.setOrdre(rubriqueQuestionDTO.getOrdre());
            return rubriqueQuestionRepository.save(rubriqueQuestion);
    }



    @Override
    public String deleteRubriqueComposee(int idRubrique){

        Rubrique rubrique = rubriqueRepository.findById(idRubrique).get();

        List<RubriqueEvaluation> rubevae = rubriqueEvaluationRepository.findByIdRubrique(rubrique);

        if(rubevae.isEmpty()){
            rubriqueQuestionRepository.deleteByRubriqueId(idRubrique);
            return "Rubrique composee est suprimee avec succes";
        }else {
            return "RUbrique composee est utilisee dans une evaluation";
        }

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
