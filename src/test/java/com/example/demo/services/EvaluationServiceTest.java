package com.example.demo.services;

import com.example.demo.DTO.*;
import com.example.demo.JWT.JwtFilter;
import com.example.demo.models.*;
import com.example.demo.repositories.EvaluationRepository;
import com.example.demo.repositories.RubriqueEvaluationRepository;
import com.example.demo.repositories.RubriqueQuestionRepository;
import com.example.demo.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class EvaluationServiceTest {

    @Mock
    private JwtFilter jwtFilter;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EvaluationRepository evaluationRepository;

    @Mock
    private RubriqueEvaluationRepository rubriqueEvaluationRepository;

    @Mock
    private RubriqueQuestionRepository rubriqueQuestionRepository;

    @InjectMocks
    private EvaluationService evaluationService;

    @Test
    void testGetEvaluationsEnseignant() {
        // Mock des données d'authentification
        Authentification authentification = new Authentification();
        Enseignant enseignant = new Enseignant();
        enseignant.setId((short) 1);
        enseignant.setEmailUbo("philippe.saliou@univ-brest.fr");
        enseignant.setNom("Saliou");
        enseignant.setPrenom("Philippe");
        authentification.setNoEnseignant(enseignant);
        when(jwtFilter.isEnseignant()).thenReturn(true);
        when(jwtFilter.getCurrentuser()).thenReturn("enseignant@example.com");
        when(userRepository.findByEmail("enseignant@example.com")).thenReturn(authentification);

        // Mock des évaluations
        Evaluation evaluation = createMockEvaluation();
        List<Evaluation> evaluations = Arrays.asList(evaluation);
        when(evaluationRepository.findByNoEnseignant(enseignant)).thenReturn(evaluations);


        // Initialisation de l'UniteEnseignement
        UniteEnseignement uniteEnseignement = new UniteEnseignement();
        UniteEnseignementId uniteEnseignementId = new UniteEnseignementId();
        uniteEnseignementId.setCodeUe("ISI"); // Assurez-vous de définir une valeur appropriée pour codeUE
        uniteEnseignement.setId(uniteEnseignementId);
        evaluation.setUniteEnseignement(uniteEnseignement);


        // Appel de la méthode à tester
        ResponseEntity<List<EvaluationDTO>> responseEntity = evaluationService.getEvaluations();

        // Vérifications
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<EvaluationDTO> evaluationsDTO = responseEntity.getBody();
        assertNotNull(evaluationsDTO);
        assertEquals(1, evaluationsDTO.size());

        // Vérification des détails de l'évaluation
        EvaluationDTO evaluationDTO = evaluationsDTO.get(0);
        assertEquals("Evaluation ISI", evaluationDTO.getDesignation());
        assertEquals("M2DOSI", evaluationDTO.getCodeFormation());
        assertEquals("ISI", evaluationDTO.getCodeUE());
        assertEquals("2014-2015", evaluationDTO.getPromotion());
        assertEquals(1, Optional.ofNullable(evaluationDTO.getNoEvaluation()).orElse((short) 0).intValue());
        assertEquals("CLO", evaluationDTO.getEtat());
        assertEquals("Du 22 septembre au 24 octobre", evaluationDTO.getPeriode());
        assertEquals(LocalDate.parse("2014-11-01"), evaluationDTO.getDebutReponse());
        assertEquals(LocalDate.parse("2014-11-12"), evaluationDTO.getFinReponse());

        // Vérification de l'enseignant
        EnseignantDTO enseignantDTO = evaluationDTO.getNoEnseignant();
        assertNotNull(enseignantDTO);
        assertEquals("philippe.saliou@univ-brest.fr", enseignantDTO.getEmailUbo());
        assertEquals(1, Optional.ofNullable(evaluationDTO.getNoEvaluation()).orElse((short) 0).intValue());
        assertEquals("Saliou", enseignantDTO.getNom());
        assertEquals("Philippe", enseignantDTO.getPrenom());


        List<EvaluationQuestionDTO> rubriques = evaluationDTO.getRubriques();
        assertNotNull(rubriques);
        //assertEquals(2, rubriques.size());
        // Vous pouvez ajouter des vérifications supplémentaires sur les rubriques selon votre logique métier
    }

    private Evaluation createMockEvaluation() {
        Evaluation evaluation = new Evaluation();
        evaluation.setNoEnseignant(new Enseignant()); // Pour éviter NullPointerException
        evaluation.getNoEnseignant().setId((short) 1);
        evaluation.getNoEnseignant().setEmailUbo("philippe.saliou@univ-brest.fr");
        evaluation.getNoEnseignant().setNom("Saliou");
        evaluation.getNoEnseignant().setPrenom("Philippe");

        // Initialisation de la Promotion
        Promotion promotion = new Promotion();
        PromotionId promotionId = new PromotionId();
        promotionId.setAnneeUniversitaire("2014-2015"); // Assurez-vous que cette valeur n'est pas null
        promotion.setId(promotionId);
        Formation formation = new Formation();
        formation.setCodeFormation("M2DOSI");
        promotion.setCodeFormation(formation);
        evaluation.setPromotion(promotion);

        // Initialisation des autres attributs de l'évaluation
        evaluation.setUniteEnseignement(new UniteEnseignement());
        evaluation.getUniteEnseignement().setId(new UniteEnseignementId());
        evaluation.setNoEvaluation((short) 1);
        evaluation.setDesignation("Evaluation ISI");
        evaluation.setEtat("CLO");
        evaluation.setPeriode("Du 22 septembre au 24 octobre");
        evaluation.setDebutReponse(LocalDate.parse("2014-11-01"));
        evaluation.setFinReponse(LocalDate.parse("2014-11-12"));

        // Initialisation des rubriques
        RubriqueEvaluation rubrique1 = new RubriqueEvaluation();
        rubrique1.setIdRubrique(new Rubrique());
        rubrique1.getIdRubrique().setId(1);
        rubrique1.getIdRubrique().setDesignation("COURS");
        rubrique1.getIdRubrique().setType("RBS");
        rubrique1.setOrdre((short) 1);

        RubriqueEvaluation rubrique2 = new RubriqueEvaluation();
        rubrique2.setIdRubrique(new Rubrique());
        rubrique2.getIdRubrique().setId(2);
        rubrique2.getIdRubrique().setDesignation("TD");
        rubrique2.getIdRubrique().setType("RBS");
        rubrique2.setOrdre((short) 2);

        // Initialisation de rubriqueEvaluations en tant que Set<RubriqueEvaluation>
        Set<RubriqueEvaluation> rubriqueEvaluations = new HashSet<>();
        rubriqueEvaluations.add(rubrique1);
        rubriqueEvaluations.add(rubrique2);

        // Affectation du Set à l'Evaluation
        evaluation.setRubriqueEvaluations(rubriqueEvaluations);

        return evaluation;
    }







    @Test
    void testGetEvaluationsNonEnseignant() {
        // Jeu d'essai
        when(jwtFilter.isEnseignant()).thenReturn(false);

        // Appel de la méthode à tester
        ResponseEntity<List<EvaluationDTO>> responseEntity = evaluationService.getEvaluations();

        // Vérifications
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals(0, responseEntity.getBody().size());
    }

    @Test
    void testGetEvaluationsException() {
        // Jeu d'essai
        when(jwtFilter.isEnseignant()).thenReturn(true);
        when(jwtFilter.getCurrentuser()).thenReturn("enseignant@example.com");
        when(userRepository.findByEmail("enseignant@example.com")).thenThrow(new RuntimeException("Erreur"));

        // Appel de la méthode à tester
        ResponseEntity<List<EvaluationDTO>> responseEntity = evaluationService.getEvaluations();

        // Vérifications
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(0, responseEntity.getBody().size());
    }

}