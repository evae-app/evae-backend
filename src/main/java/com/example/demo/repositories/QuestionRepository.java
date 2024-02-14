package com.example.demo.repositories;

import com.example.demo.models.Question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
	
	@Query("select q from Question q where q.intitule = :intitule")
	Question findByIntitule(@Param("intitule") String intitule);
}
