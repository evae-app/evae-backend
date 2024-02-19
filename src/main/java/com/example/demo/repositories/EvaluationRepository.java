package com.example.demo.repositories;

import com.example.demo.models.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Evaluation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, Integer>{

	@Query("select ev from Evaluation ev where ev.noEnseignant = :noEnseignant")
	List<Evaluation> findByNoEnseignant(@Param("noEnseignant") Enseignant id);
	
	Evaluation findByNoEvaluation(Short noEvaluation);

}
