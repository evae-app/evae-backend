package com.example.demo.services;

import com.example.demo.DTO.QuestionDTO;
import com.example.demo.JWT.JwtFilter;
import com.example.demo.constants.EvaeBackendConstants;
import com.example.demo.models.Qualificatif;
import com.example.demo.models.Question;
import com.example.demo.models.QuestionEvaluation;
import com.example.demo.models.RubriqueQuestion;
import com.example.demo.repositories.QualificatifRepository;
import com.example.demo.repositories.QuestionEvaluationRepository;
import com.example.demo.repositories.QuestionRepository;
import com.example.demo.repositories.RubriqueQuestionRepository;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {
    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private QualificatifRepository qualificatifRepository;

    @Mock
    private RubriqueQuestionRepository rubriqueQuestionRepository;

    @Mock
    private QuestionEvaluationRepository questionEvaluationRepository;

    @Mock
    private JwtFilter jwtFilter;

    @InjectMocks
    private QuestionService questionService;

    @Test
    void ajouterQuestion_Success() {
        // Given
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("intitule", "Sample Intitule");
        requestMap.put("minimal", "Sample Minimal");

        when(qualificatifRepository.findByMinmal(any(String.class))).thenReturn(new Qualificatif());
        when(questionRepository.findByIntitule(any(String.class))).thenReturn(null);

        // When
        ResponseEntity<String> responseEntity = questionService.AjouterQuestion(requestMap);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("{\"message\":\"Question Successfully Registered\"}", responseEntity.getBody());
    }

    @Test
    void ajouterQuestion_Failure_AlreadyExists() {
        // Given
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("intitule", "Sample Intitule");
        requestMap.put("minimal", "Sample Minimal");

        when(qualificatifRepository.findByMinmal(any(String.class))).thenReturn(new Qualificatif());
        when(questionRepository.findByIntitule(any(String.class))).thenReturn(new Question());

        // When
        ResponseEntity<String> responseEntity = questionService.AjouterQuestion(requestMap);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("{\"message\":\"Question already exists\"}", responseEntity.getBody());
    }

    @Test
    void getQuestions_Admin() {
        // Given
        when(jwtFilter.isAdmin()).thenReturn(true);

        List<Question> mockQuestions = new ArrayList<>();
        Question question1 = new Question();
        question1.setId(1);
        question1.setIntitule("Question 1");
        question1.setIdQualificatif(new Qualificatif());
        mockQuestions.add(question1);

        Question question2 = new Question();
        question2.setId(2);
        question2.setIntitule("Question 2");
        question2.setIdQualificatif(new Qualificatif());
        mockQuestions.add(question2);

        when(questionRepository.findAll()).thenReturn(mockQuestions);

        // When
        ResponseEntity<List<QuestionDTO>> responseEntity = questionService.getQuestions();

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().size());
        // You can add more assertions to verify the content of the response if needed
    }

    @Test
    void getQuestions_NotAdmin() {
        // Given
        when(jwtFilter.isAdmin()).thenReturn(false);

        // When
        ResponseEntity<List<QuestionDTO>> responseEntity = questionService.getQuestions();

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals(0, responseEntity.getBody().size());
    }

    @Test
    void update_Admin_Success() {
        // Given
        when(jwtFilter.isAdmin()).thenReturn(true);

        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("id", "1");
        requestMap.put("minimal", "Minimal Value");
        requestMap.put("intitule", "Updated Intitule");

        Question question = new Question();
        question.setId(1);
        when(questionRepository.findById(any(Integer.class))).thenReturn(Optional.of(question));
        when(qualificatifRepository.findByMinmal(any(String.class))).thenReturn(new Qualificatif());
        when(rubriqueQuestionRepository.findByQuestion(any(Question.class))).thenReturn(null);
        when(questionEvaluationRepository.findByQuestion(any(Question.class))).thenReturn(null);

        // When
        ResponseEntity<String> responseEntity = questionService.update(requestMap);

        // Extracting the message from the JSON object
        String jsonResponse = responseEntity.getBody();
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        String actualMessage = jsonObject.get("message").getAsString();

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Updated Successfully", actualMessage);
    }

    @Test
    void update_Admin_Failure_RubriqueQuestionExists() {
        // Given
        when(jwtFilter.isAdmin()).thenReturn(true);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("id", "1");
        requestMap.put("minimal", "Minimal Value");
        requestMap.put("intitule", "Updated Intitule");

        Question question = new Question();
        question.setId(1);
        when(questionRepository.findById(any(Integer.class))).thenReturn(Optional.of(question));
        when(qualificatifRepository.findByMinmal(any(String.class))).thenReturn(new Qualificatif());
        // Simuler que la question est liée à une rubrique dans une évaluation
        when(rubriqueQuestionRepository.findByQuestion(any(Question.class))).thenReturn(new RubriqueQuestion());
        when(questionEvaluationRepository.findByQuestion(any(Question.class))).thenReturn(new QuestionEvaluation());

        // When
        ResponseEntity<String> responseEntity = questionService.update(requestMap);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("{\"message\":\"Question est utilisée dans une Rubrique dans une Evaluation\"}", responseEntity.getBody());
    }


    @Test
    void update_NotAdmin() {
        // Given
        when(jwtFilter.isAdmin()).thenReturn(false);

        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("id", "1");
        requestMap.put("minimal", "Minimal Value");
        requestMap.put("intitule", "Updated Intitule");

        // When
        ResponseEntity<String> responseEntity = questionService.update(requestMap);

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals("{\"message\":\"Unauthorized access.\"}", responseEntity.getBody());
    }

    @Test
    void deleteQuestion_SuccessfulDeletion_Admin() {
        // Given
        when(jwtFilter.isAdmin()).thenReturn(true);
        int questionId = 1;
        Question question = new Question();
        question.setId(questionId);
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));
        when(rubriqueQuestionRepository.findByQuestion(question)).thenReturn(null);
        when(questionEvaluationRepository.findByQuestion(question)).thenReturn(null);

        // When
        ResponseEntity<String> responseEntity = questionService.deleteQuestion(questionId);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("{\"message\":\"Question Deleted Successfully\"}", responseEntity.getBody());
    }

    @Test
    void deleteQuestion_Failure_QuestionUsedInRubriqueOrEvaluation_Admin() {
        // Given
        when(jwtFilter.isAdmin()).thenReturn(true);
        int questionId = 1;
        Question question = new Question();
        question.setId(questionId);
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));
        when(rubriqueQuestionRepository.findByQuestion(question)).thenReturn(new RubriqueQuestion());
        when(questionEvaluationRepository.findByQuestion(question)).thenReturn(new QuestionEvaluation());

        // When
        ResponseEntity<String> responseEntity = questionService.deleteQuestion(questionId);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("{\"message\":\"Question est utilisée dans une Rubrique dans une Evaluation\"}", responseEntity.getBody());
    }


    @Test
    void deleteQuestion_NegativeId_Admin() {
        // Given
        when(jwtFilter.isAdmin()).thenReturn(true);
        int questionId = -1;

        // When
        ResponseEntity<String> responseEntity = questionService.deleteQuestion(questionId);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("{\"message\":\"Negative Numbers Not Supported , Please Give a Positive Number\"}", responseEntity.getBody());
    }

    @Test
    void deleteQuestion_UnauthorizedAccess() {
        // Given
        when(jwtFilter.isAdmin()).thenReturn(false);

        // When
        ResponseEntity<String> responseEntity = questionService.deleteQuestion(1);

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().contains("Unauthorized access."));
    }


    /*
    @Test
    void update_Admin_Failure_RubriqueQuestionExists() {
        // Given
        when(jwtFilter.isAdmin()).thenReturn(true);

        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("id", "1");
        requestMap.put("minimal", "Minimal Value");
        requestMap.put("intitule", "Updated Intitule");

        Question question = new Question();
        question.setId(1);
        when(questionRepository.findById(any(Integer.class))).thenReturn(Optional.of(question));
        when(qualificatifRepository.findByMinmal(any(String.class))).thenReturn(new Qualificatif());
        when(rubriqueQuestionRepository.findByQuestion(any(Question.class))).thenReturn(new RubriqueQuestion());

        // When
        ResponseEntity<String> responseEntity = questionService.update(requestMap);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("{\"message\":\"Question est utilisée dans une Rubrique dans une Evaluation\"}", responseEntity.getBody());
    }
    */

}


