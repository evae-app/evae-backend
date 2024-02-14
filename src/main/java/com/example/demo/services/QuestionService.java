package com.example.demo.services;

import com.example.demo.DTO.QuestionDTO;
import com.example.demo.models.Question;
import com.example.demo.models.QuestionEvaluation;
import com.example.demo.models.RubriqueQuestion;
import com.example.demo.JWT.CustomerUserDetailsService;
import com.example.demo.JWT.JwtFilter;
import com.example.demo.JWT.JwtUtil;
import com.example.demo.constants.EvaeBackendConstants;
import com.example.demo.models.Qualificatif;
import com.example.demo.repositories.QualificatifRepository;
import com.example.demo.repositories.QuestionEvaluationRepository;
import com.example.demo.repositories.QuestionRepository;
import com.example.demo.repositories.RubriqueQuestionRepository;
import com.example.demo.utils.BackendUtils;
import com.example.demo.utils.EmailUtils;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QuestionService {
	
	@Autowired
    QuestionRepository questionRepository;
	
	@Autowired
	QualificatifRepository qualificatifRepository;
	
	@Autowired
	RubriqueQuestionRepository RubriquequestionRepository;
	
	@Autowired
	QuestionEvaluationRepository QuestionevaluationRepository;
	
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
	
	public ResponseEntity<String> AjouterQuestion(Map<String, String> requestMap){
        System.out.println("Inside Ajout Question:" + requestMap);
    	try {
            if (requestMap.containsKey("intitule")) {
            	if(!TestService.isInteger(requestMap.get("intitule"))) {
            		if(!requestMap.get("intitule").isEmpty()) {
            			Question question1 = questionRepository.findByIntitule(requestMap.get("intitule"));
            			Qualificatif qualificatif = qualificatifRepository.findByMinmal(requestMap.get("minimal"));
            			
                        if (Objects.isNull(question1)) {
                        	Question question = new Question();
                        	question.setIntitule(requestMap.get("intitule"));
                        	question.setNoEnseignant(null);
                        	question.setId(Integer.parseInt(requestMap.get("id")));
                        	question.setIdQualificatif(qualificatif);
                        	question.setType("standard");
                        	
                        	questionRepository.save(question);
                            return BackendUtils.getResponseEntity("Question Successfully Registered", HttpStatus.OK);
                        } else {
                            return BackendUtils.getResponseEntity("Question already exists", HttpStatus.BAD_REQUEST);
                        }
            		}else {
            			return BackendUtils.getResponseEntity("Missing intitule Question Value", HttpStatus.BAD_REQUEST);
            		}
            		
            	}else {
            		return BackendUtils.getResponseEntity("Please Give a String Value to intitule Question ", HttpStatus.BAD_REQUEST);
            	}
                
            } else {
                return BackendUtils.getResponseEntity(EvaeBackendConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return BackendUtils.getResponseEntity(EvaeBackendConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	
	public ResponseEntity<List<QuestionDTO>> getQuestions(){
		try {
			if(jwtFilter.isAdmin()) {
				List<com.example.demo.models.Question> questions = questionRepository.findAll();
				List<QuestionDTO> QuestionDTOs = new ArrayList<>();

	            for (Question question : questions) {
	            	QuestionDTO QuestionDTO = new QuestionDTO();

	            	QuestionDTO.setId(question.getId());
	            	QuestionDTO.setIntitule(question.getIntitule());
	            	QuestionDTO.setIdQualificatif(question.getIdQualificatif());

	            	QuestionDTOs.add(QuestionDTO);
	            }

	            return new ResponseEntity<>(QuestionDTOs, HttpStatus.OK);
			}else {
				return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                        Question question = questionRepository.findById(Integer.parseInt(requestMap.get("id"))).get();
                        Qualificatif qualificatif = qualificatifRepository.findByMinmal(requestMap.get("minimal"));
                        RubriqueQuestion RubriqueQuestion = RubriquequestionRepository.findByQuestion(question);
                        QuestionEvaluation QuestionEvaluation = QuestionevaluationRepository.findByQuestion(question);
                        
                        if(RubriqueQuestion == null || QuestionEvaluation == null) {
                        	System.out.println("question : " + question);
                            if (question != null) {
                                question.setId(Integer.parseInt(requestMap.get("id")));
                                question.setIdQualificatif(qualificatif);
                                question.setIntitule(requestMap.get("intitule"));
                                question.setNoEnseignant(null);
                                question.setType("standard");
                                
                                questionRepository.save(question);
                                return BackendUtils.getResponseEntity(EvaeBackendConstants.USER_STATUS, HttpStatus.OK);
                            } else {
                                return BackendUtils.getResponseEntity("Question id doesn't exist.", HttpStatus.BAD_REQUEST);
                            }
                        }else {
                        	return BackendUtils.getResponseEntity("Question est utilisée dans une Rubrique dans une Evaluation", HttpStatus.BAD_REQUEST);
                        }
                        
                    

            } else {
                return BackendUtils.getResponseEntity(EvaeBackendConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (NumberFormatException e) {
            // Handle the NumberFormatException (e.g., invalid numeric input)
            return BackendUtils.getResponseEntity("Invalid input format for numeric values.", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace();
        }
        return BackendUtils.getResponseEntity(EvaeBackendConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


	
	public ResponseEntity<String> deleteQuestion(int idQuestion){
		try {
			if(jwtFilter.isAdmin()) {
				if(TestService.intPositifNegatif(idQuestion)) {
					Question question = questionRepository.findById(idQuestion).get();
					RubriqueQuestion RubriqueQuestion = RubriquequestionRepository.findByQuestion(question);
                    QuestionEvaluation QuestionEvaluation = QuestionevaluationRepository.findByQuestion(question);
                    if(RubriqueQuestion == null || QuestionEvaluation == null) {
                    	questionRepository.delete(question);
    					return BackendUtils.getResponseEntity("Question Deleted Successfully", HttpStatus.OK);
                    }else {
                    	return BackendUtils.getResponseEntity("Question est utilisée dans une Rubrique dans une Evaluation", HttpStatus.BAD_REQUEST);
                    }
					
				}else {
					return BackendUtils.getResponseEntity("Negative Numbers Not Supported , Please Give a Positive Number", HttpStatus.BAD_REQUEST);
				}
				
				
			}else {
				return BackendUtils.getResponseEntity(EvaeBackendConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return BackendUtils.getResponseEntity(EvaeBackendConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
