package com.example.demo.repositories;

import java.util.List;

import com.example.demo.models.Rubrique;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Evaluation;
import com.example.demo.models.RubriqueEvaluation;

public interface RubriqueEvaluationRepository extends JpaRepository<RubriqueEvaluation, Integer>{
	
	List<RubriqueEvaluation> findByIdEvaluation(Evaluation evae);

	List<RubriqueEvaluation> findByIdRubrique(Rubrique rubrique);

}
