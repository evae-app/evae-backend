package com.example.demo.services;

import com.example.demo.DTO.ElementConstitutifDTO;
import com.example.demo.DTO.EnseignantDTO;
import com.example.demo.DTO.EvaluationDTO;
import com.example.demo.DTO.QualificatifDTO;
import com.example.demo.DTO.QuestionDTO;
import com.example.demo.DTO.EvaluationQuestionDTO;
import com.example.demo.JWT.CustomerUserDetailsService;
import com.example.demo.JWT.JwtFilter;
import com.example.demo.JWT.JwtUtil;
import com.example.demo.constants.EvaeBackendConstants;
import com.example.demo.models.*;
import com.example.demo.repositories.*;
import com.example.demo.utils.BackendUtils;
import com.example.demo.utils.EmailUtils;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EvaluationService {


	@Autowired
	UserRepository userRepository;

	@Autowired
	EvaluationRepository evaluationRepository;

	@Autowired
	ElementConstitutifRepository elementConstitutifRepository;

	@Autowired
	EnseignantRepository enseignantRepository;

	@Autowired
	PromotionRepository promotionRepository;

	@Autowired
	RubriqueQuestionRepository RubriquequestionRepository;

	@Autowired
	QuestionEvaluationRepository QuestionevaluationRepository;

	@Autowired
	RubriqueEvaluationRepository rubriqueEvaluationRepository;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	CustomerUserDetailsService customerUserDetailsService;

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	JwtFilter jwtFilter;

	@Autowired
	EmailUtils emailUtils;
	
/*	public ResponseEntity<String> AjouterEvaluation(Map<String, String> requestMap){
        System.out.println("Inside Ajout Evaluation:" + requestMap);
    	try {
            if (requestMap.containsKey("noEvaluation")) {
            		if(!requestMap.get("noEvaluation").isEmpty()) {
            			Evaluation evaluation = evaluationRepository.findByNoEvaluation(Short.parseShort(requestMap.get("noEvaluation")));
            			
            			ElementConstitutifId ecId = new ElementConstitutifId();
            			ecId.setCodeFormation(requestMap.get("codeFormation"));
            			ecId.setCodeEc(requestMap.get("codeEc"));
            			ecId.setCodeUe(requestMap.get("codeUe"));
            			
            			ElementConstitutif ec = elementConstitutifRepository.findByElementConstitutifId(ecId);
            			
            			Enseignant ens = enseignantRepository.findById(Short.parseShort(requestMap.get("idEnseignant"))).get();
            			
            			PromotionId proId = new PromotionId();
            			proId.setAnneeUniversitaire(requestMap.get("anneePro"));
            			proId.setCodeFormation(requestMap.get("codeFormation"));
            			
            			Promotion pro = promotionRepository.findByPromotionId(proId);
            			
            			Set<RubriqueEvaluation> rubriqueEvaluations = requestMap.get("Rubriques");
            			
                        if (Objects.isNull(evaluation)) {
                        	Evaluation evae = new Evaluation();
                        	
                        	evae.setDebutReponse(LocalDate.parse(requestMap.get("debutReponse")));
                        	evae.setDesignation(requestMap.get("designation"));
                        	evae.setElementConstitutif(ec);
                        	evae.setEtat(requestMap.get("etat"));
                        	evae.setFinReponse(LocalDate.parse(requestMap.get("finReponse")));
                        	evae.setNoEnseignant(ens);
                        	evae.setNoEvaluation(Short.parseShort(requestMap.get("noEvaluation")));
                        	evae.setPeriode(requestMap.get("periode"));
                        	evae.setPromotion(pro);
                        	
                        	
                        	evaluationRepository.save(evae);
                            return BackendUtils.getResponseEntity("Evaluation Successfully Registered", HttpStatus.OK);
                        } else {
                            return BackendUtils.getResponseEntity("Evaluation already exists", HttpStatus.BAD_REQUEST);
                        }
            		}else {
            			return BackendUtils.getResponseEntity("Missing numero Evaluation Value", HttpStatus.BAD_REQUEST);
            		}
                
            } else {
                return BackendUtils.getResponseEntity(EvaeBackendConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return BackendUtils.getResponseEntity(EvaeBackendConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }*/


	public ResponseEntity<List<EvaluationDTO>> getEvaluations() {
		try {
			if (jwtFilter.isEnseignant()) {
				Authentification user = userRepository.findByEmail(jwtFilter.getCurrentuser());
				List<Evaluation> evaluations = evaluationRepository.findByNoEnseignant(user.getNoEnseignant());
				List<EvaluationDTO> evaluationsDTO = new ArrayList<>();

				for (Evaluation evaluation : evaluations) {
					EvaluationDTO evaluationDTO = new EvaluationDTO();
					evaluationDTO.setDebutReponse(evaluation.getDebutReponse());
					evaluationDTO.setDesignation(evaluation.getDesignation());
					evaluationDTO.setCodeUE(evaluation.getUniteEnseignement().getId().getCodeUe());
					evaluationDTO.setCodeFormation(evaluation.getPromotion().getCodeFormation().getCodeFormation());
					evaluationDTO.setEtat(evaluation.getEtat());
					evaluationDTO.setFinReponse(evaluation.getFinReponse());
					evaluationDTO.setId(evaluation.getId());
					evaluationDTO.setNoEvaluation(evaluation.getNoEvaluation());
					evaluationDTO.setPeriode(evaluation.getPeriode());
					evaluationDTO.setPromotion(evaluation.getPromotion().getId().getAnneeUniversitaire());

					EnseignantDTO ens = new EnseignantDTO();
					ens.setEmailUbo(evaluation.getNoEnseignant().getEmailUbo());
					ens.setId(evaluation.getNoEnseignant().getId());
					ens.setNom(evaluation.getNoEnseignant().getNom());
					ens.setPrenom(evaluation.getNoEnseignant().getPrenom());
					evaluationDTO.setNoEnseignant(ens);

					List<RubriqueEvaluation> rubevae = rubriqueEvaluationRepository.findByIdEvaluation(evaluation);
					List<EvaluationQuestionDTO> rqs = new ArrayList<>();

					for (RubriqueEvaluation rubevaluation : rubevae) {
						EvaluationQuestionDTO evaluationQuestionDTO = new EvaluationQuestionDTO();
						evaluationQuestionDTO.setIdRubrique(rubevaluation.getIdRubrique().getId());
						evaluationQuestionDTO.setOrdre(Long.valueOf(rubevaluation.getOrdre()));
						evaluationQuestionDTO.setDesignation(rubevaluation.getIdRubrique().getDesignation());
						evaluationQuestionDTO.setType(rubevaluation.getIdRubrique().getType());

						Set<QuestionDTO> uniqueQuestions = new LinkedHashSet<>();
						List<RubriqueQuestion> rubriqueQuestions = RubriquequestionRepository.findByIdRubrique(rubevaluation.getIdRubrique().getId());
						for (RubriqueQuestion rubriqueQuestion : rubriqueQuestions) {
							QuestionDTO questionDTO = new QuestionDTO();
							questionDTO.setId(rubriqueQuestion.getIdQuestion().getId());
							questionDTO.setIntitule(rubriqueQuestion.getIdQuestion().getIntitule());
							questionDTO.setType(rubriqueQuestion.getIdQuestion().getType());
							questionDTO.setOrdre(rubriqueQuestion.getOrdre());

							QualificatifDTO qualificatifDTO = new QualificatifDTO();
							qualificatifDTO.setId(rubriqueQuestion.getIdQuestion().getIdQualificatif().getId());
							qualificatifDTO.setMaximal(rubriqueQuestion.getIdQuestion().getIdQualificatif().getMaximal());
							qualificatifDTO.setMinimal(rubriqueQuestion.getIdQuestion().getIdQualificatif().getMinimal());
							questionDTO.setIdQualificatif(qualificatifDTO);

							uniqueQuestions.add(questionDTO);
						}

						List<QuestionDTO> questionsList = new ArrayList<>(uniqueQuestions);
						evaluationQuestionDTO.setQuestions(questionsList);
						rqs.add(evaluationQuestionDTO);
					}

					evaluationDTO.setRubriques(rqs);
					evaluationsDTO.add(evaluationDTO);
				}

				return new ResponseEntity<>(evaluationsDTO, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
			}
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}