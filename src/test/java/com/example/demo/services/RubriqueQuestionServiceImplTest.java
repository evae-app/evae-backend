package com.example.demo.services;


import com.example.demo.DTO.RubriqueQuestionDTO;
import com.example.demo.models.Qualificatif;
import com.example.demo.models.Question;
import com.example.demo.models.Rubrique;
import com.example.demo.models.RubriqueQuestion;
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





    /*@Test
    void testCreateRubriqueQuestion() {
        // Création d'un DTO de RubriqueQuestion fictif pour le test
        RubriqueQuestionDTO rubriqueQuestionDTO = new RubriqueQuestionDTO();
        rubriqueQuestionDTO.setIdRubrique(1);
        rubriqueQuestionDTO.setIdQuestion(1);
        rubriqueQuestionDTO.setOrdre(1L);

        // Simulation du comportement du repository pour ne pas trouver de doublon ou d'ordre existant
        when(rubriqueQuestionRepository.existsById_IdRubriqueAndId_IdQuestion(anyInt(), anyInt())).thenReturn(false);
        when(rubriqueQuestionRepository.existsById_IdRubriqueAndOrdre(anyInt(), anyLong())).thenReturn(false);

        // Simulation du comportement des repositories pour retourner les entités associées
        when(rubriqueRepository.findById(anyInt())).thenReturn(Optional.of(new Rubrique()));
        when(questionRepository.findById(anyInt())).thenReturn(Optional.of(new Question()));

        // Appel de la méthode à tester
        RubriqueQuestion createdRubriqueQuestion = rubriqueQuestionService.createRubriqueQuestion(rubriqueQuestionDTO);

        // Vérifications
        assertNotNull(createdRubriqueQuestion);
        assertEquals(rubriqueQuestionDTO.getIdRubrique(), createdRubriqueQuestion.getId().getIdRubrique());
        assertEquals(rubriqueQuestionDTO.getIdQuestion(), createdRubriqueQuestion.getId().getIdQuestion());
        assertEquals(rubriqueQuestionDTO.getOrdre(), createdRubriqueQuestion.getOrdre());

        // Vérification des appels aux méthodes du repository
        verify(rubriqueQuestionRepository, times(1)).save(any());
    }
*/



}
