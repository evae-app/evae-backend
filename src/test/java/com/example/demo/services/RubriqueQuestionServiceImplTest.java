package com.example.demo.services;


import com.example.demo.DTO.RubriqueQuestionDTO;
import com.example.demo.models.*;
import com.example.demo.repositories.QuestionRepository;
import com.example.demo.repositories.RubriqueQuestionRepository;
import com.example.demo.repositories.RubriqueRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RubriqueQuestionServiceImplTest {

    @Mock
    private RubriqueQuestionRepository rubriqueQuestionRepository;

    @Mock
    private RubriqueRepository rubriqueRepository;

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private RubriqueQuestionServiceImpl rubriqueQuestionService;

    @Test
    void getAllRubriqueQuestion() {
        // Création de données fictives pour le test
        List<RubriqueQuestion> rubriqueQuestions = new ArrayList<>();
        RubriqueQuestion rubriqueQuestion1 = createMockRubriqueQuestion(1, 1, 1);
        RubriqueQuestion rubriqueQuestion2 = createMockRubriqueQuestion(1, 2, 2);
        rubriqueQuestions.add(rubriqueQuestion1);
        rubriqueQuestions.add(rubriqueQuestion2);

        // Mock de la méthode findAll du repository
        when(rubriqueQuestionRepository.findAll()).thenReturn(rubriqueQuestions);

        // Appel de la méthode à tester
        List<RubriqueQuestionDTO> result = rubriqueQuestionService.getAllRubriqueQuestion();

        // Vérifications
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getIdRubrique());
        assertEquals(1, result.get(0).getIdQuestion());
        assertEquals(1, result.get(0).getOrdre());
        assertEquals("COURS", result.get(0).getRubriqueDTO().getDesignation());
        assertEquals("Contenu", result.get(0).getQuestionDTO().getIntitule());
        assertEquals("Pauvre", result.get(0).getQuestionDTO().getIdQualificatif().getMaximal());

        assertEquals(1, result.get(1).getIdRubrique());
        assertEquals(2, result.get(1).getIdQuestion());
        assertEquals(2, result.get(1).getOrdre());
        assertEquals("COURS", result.get(1).getRubriqueDTO().getDesignation());
        assertEquals("Contenu", result.get(1).getQuestionDTO().getIntitule());
        assertEquals("Pauvre", result.get(1).getQuestionDTO().getIdQualificatif().getMaximal());
    }

    private RubriqueQuestion createMockRubriqueQuestion(int idRubrique, int idQuestion, int ordre) {
        RubriqueQuestion rubriqueQuestion = new RubriqueQuestion();
        Rubrique rubrique = new Rubrique();
        rubrique.setId(idRubrique);

        // Création et initialisation de la Question
        Question question = new Question();
        question.setId(idQuestion);

        // Initialisation de RubriqueQuestion avec Rubrique et Question
        rubriqueQuestion.setIdRubrique(rubrique);
        rubriqueQuestion.setIdQuestion(question);
        rubriqueQuestion.setOrdre((long) ordre);

        // Création des entités liées (Rubrique, Question, Qualificatif)
        // Ces données peuvent être simplifiées selon votre besoin réel pour les tests
        rubrique.setId(idRubrique);
        rubrique.setType("RBS");
        rubrique.setDesignation("COURS");
        rubrique.setOrdre(1L);

        question.setId(idQuestion);
        question.setType("QUS");
        question.setIntitule("Contenu");

        Qualificatif qualificatif = new Qualificatif();
        qualificatif.setId(1);
        qualificatif.setMaximal("Pauvre");

        question.setIdQualificatif(qualificatif);

        rubriqueQuestion.setIdRubrique(rubrique);
        rubriqueQuestion.setIdQuestion(question);

        return rubriqueQuestion;
    }


    @Test
    void testGetQuestionsByRubrique() {
        // Création d'une rubrique fictive pour le test
        Rubrique rubrique = new Rubrique();
        rubrique.setId(1);
        rubrique.setType("RBS");
        rubrique.setDesignation("COURS");
        rubrique.setOrdre(1L);

        // Création de données fictives pour le test
        Set<Question> questions = new HashSet<>();
        Question question1 = new Question();
        question1.setId(1);
        question1.setType("QUS");
        question1.setIntitule("Contenu");
        questions.add(question1);

        Question question2 = new Question();
        question2.setId(2);
        question2.setType("QUS");
        question2.setIntitule("Intérêt");
        questions.add(question2);

        // Simulation du comportement du repository
        when(rubriqueQuestionRepository.findQuestionsByRubrique(rubrique)).thenReturn(questions);

        // Appel de la méthode à tester
        Set<Question> result = rubriqueQuestionService.getQuestionsByRubrique(rubrique);

        // Vérifications
        assertEquals(2, result.size());
        assertEquals("Contenu", result.stream().filter(q -> q.getId() == 1).findFirst().get().getIntitule());
        assertEquals("Intérêt", result.stream().filter(q -> q.getId() == 2).findFirst().get().getIntitule());
    }




    @Test
    void testCreateRubriqueQuestion_WhenNotExists() {
        // Création de données de test
        RubriqueQuestionDTO rubriqueQuestionDTO = new RubriqueQuestionDTO();
        rubriqueQuestionDTO.setIdRubrique(1);
        rubriqueQuestionDTO.setIdQuestion(2);
        rubriqueQuestionDTO.setOrdre(1L);

        RubriqueQuestion rubriqueQuestion = new RubriqueQuestion();
        rubriqueQuestion.setId(new RubriqueQuestionId(1, 2));
        rubriqueQuestion.setOrdre(1L); // Ajout de l'ordre à la rubrique de question

        Rubrique rubrique = new Rubrique();
        rubrique.setId(1);

        Question question = new Question();
        question.setId(2);

        // Définir le comportement simulé des repositories
        when(rubriqueQuestionRepository.existsById_IdRubriqueAndId_IdQuestion(1, 2)).thenReturn(false);
        when(rubriqueRepository.findById(1)).thenReturn(Optional.of(rubrique));
        when(questionRepository.findById(2)).thenReturn(Optional.of(question));
        when(rubriqueQuestionRepository.save(any())).thenReturn(rubriqueQuestion);

        // Appeler la méthode à tester
        RubriqueQuestion createdRubriqueQuestion = rubriqueQuestionService.createRubriqueQuestion(rubriqueQuestionDTO);

        // Vérifier les résultats
        assertNotNull(createdRubriqueQuestion);
        assertEquals(1, createdRubriqueQuestion.getId().getIdRubrique());
        assertEquals(2, createdRubriqueQuestion.getId().getIdQuestion());
        assertEquals(1L, createdRubriqueQuestion.getOrdre()); // Correction : vérifier l'ordre correctement initialisé
    }


}





