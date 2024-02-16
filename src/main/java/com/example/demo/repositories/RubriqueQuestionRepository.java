package com.example.demo.repositories;

import com.example.demo.models.Question;
import com.example.demo.models.Rubrique;
import com.example.demo.models.RubriqueQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;


public interface RubriqueQuestionRepository extends JpaRepository<RubriqueQuestion, Integer>{
	
	@Query("select rq from RubriqueQuestion rq where rq.idQuestion = :question")
	RubriqueQuestion findByQuestion(@Param("question") Question question);

	@Query("SELECT rq.idQuestion FROM RubriqueQuestion rq WHERE rq.idRubrique = :rubrique")
	Set<Question> findQuestionsByRubrique(@Param("rubrique") Rubrique rubrique);

	boolean existsById_IdRubriqueAndId_IdQuestion(
			Integer rubriqueId,
			Integer questionId
	);

	boolean existsById_IdRubriqueAndOrdre(
			Integer rubriqueId,
			Long ordre
	);


}
