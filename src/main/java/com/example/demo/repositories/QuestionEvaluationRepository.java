package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.models.Question;
import com.example.demo.models.QuestionEvaluation;

public interface QuestionEvaluationRepository extends JpaRepository<QuestionEvaluation,Integer> {
	
	@Query("select qe from QuestionEvaluation qe where qe.idQuestion = :question")
	QuestionEvaluation findByQuestion(@Param("question") Question question);

}
