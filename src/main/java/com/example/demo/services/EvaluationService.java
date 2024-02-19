package com.example.demo.services;

import com.example.demo.DTO.ElementConstitutifDTO;
import com.example.demo.DTO.EnseignantDTO;
import com.example.demo.DTO.EvaluationDTO;
import com.example.demo.DTO.QualificatifDTO;
import com.example.demo.DTO.QuestionDTO;
import com.example.demo.DTO.RubriqueQuestionDTO;
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
	
	
	public ResponseEntity<List<EvaluationDTO>> getEvaluations(){
		try {
			if(jwtFilter.isEnseignant()) {

				Authentification user = userRepository.findByEmail(jwtFilter.getCurrentuser());
				List<Evaluation> evaluations = evaluationRepository.findByNoEnseignant(user.getNoEnseignant());
				List<EvaluationDTO> evaluationsDTO = new ArrayList<>();

	            for (Evaluation evaluation : evaluations) {
	            	EvaluationDTO evaluationDTO = new EvaluationDTO();
	            	List<RubriqueEvaluation> rubevae = rubriqueEvaluationRepository.findByIdEvaluation(evaluation);
	            	List<RubriqueQuestionDTO> rqs = new ArrayList<>();
	            	List<QuestionDTO> questions = new ArrayList<>();
	            	
	            	for(RubriqueEvaluation rubevaluation : rubevae) {
	            		RubriqueQuestionDTO RubriqueQuestionDTO = new RubriqueQuestionDTO();
	            		RubriqueQuestionDTO.setIdRubrique(rubevaluation.getIdRubrique().getId());
	            		RubriqueQuestionDTO.setOrdre(rubevaluation.getOrdre());
	            		RubriqueQuestionDTO.setDesignation(rubevaluation.getIdRubrique().getDesignation());
	            		RubriqueQuestionDTO.setType(rubevaluation.getIdRubrique().getType());
	            		
	            		List<RubriqueQuestion> RubriqueQuestions = RubriquequestionRepository.findByIdRubrique(rubevaluation.getIdRubrique().getId());
	            		for(RubriqueQuestion RubriqueQuestion : RubriqueQuestions) {
	            			QuestionDTO questionDTO = new QuestionDTO();
	            			questionDTO.setId(RubriqueQuestion.getIdQuestion().getId());
	            			questionDTO.setIntitule(RubriqueQuestion.getIdQuestion().getIntitule());
	            			questionDTO.setType(RubriqueQuestion.getIdQuestion().getType());
	            			
	            			QualificatifDTO qualificatifDTO = new QualificatifDTO();
	            			qualificatifDTO.setId(RubriqueQuestion.getIdQuestion().getIdQualificatif().getId());
	            			qualificatifDTO.setMaximal(RubriqueQuestion.getIdQuestion().getIdQualificatif().getMaximal());
	            			qualificatifDTO.setMinimal(RubriqueQuestion.getIdQuestion().getIdQualificatif().getMinimal());
	            			
	            			questionDTO.setIdQualificatif(qualificatifDTO);
	            			
	            			questions.add(questionDTO);
	            		}
	            		
	            		RubriqueQuestionDTO.setQuestions(questions);
	            		
	            		
	            		rqs.add(RubriqueQuestionDTO);
	            	}
	            	
	            	evaluationDTO.setDebutReponse(evaluation.getDebutReponse());
	            	evaluationDTO.setDesignation(evaluation.getDesignation());

					if (evaluation.getElementConstitutif() != null) {
						evaluationDTO.setCodeEC(evaluation.getElementConstitutif().getId().getCodeEc());
					}

					evaluationDTO.setCodeUE(evaluation.getUniteEnseignement().getId().getCodeUe());

					evaluationDTO.setCodeFormation(evaluation.getPromotion().getCodeFormation().getCodeFormation());

					evaluationDTO.setEtat(evaluation.getEtat());
	            	evaluationDTO.setFinReponse(evaluation.getFinReponse());
	            	evaluationDTO.setId(evaluation.getId());
	            	
	            	EnseignantDTO ens = new EnseignantDTO(); 
	            	ens.setEmailUbo(evaluation.getNoEnseignant().getEmailUbo());
	            	ens.setId(evaluation.getNoEnseignant().getId());
	            	ens.setNom(evaluation.getNoEnseignant().getNom());
	            	ens.setPrenom(evaluation.getNoEnseignant().getPrenom());
	            	
	            	evaluationDTO.setNoEnseignant(ens);
	            	evaluationDTO.setNoEvaluation(evaluation.getNoEvaluation());
	            	evaluationDTO.setPeriode(evaluation.getPeriode());
	            	
	            	
	            	evaluationDTO.setPromotion(evaluation.getPromotion().getId().getAnneeUniversitaire());
	            	
	            	
	            	evaluationDTO.setRubriques(rqs);

	            	
	            	evaluationsDTO.add(evaluationDTO);
	            }

	            return new ResponseEntity<>(evaluationsDTO, HttpStatus.OK);
			}else {
				return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}