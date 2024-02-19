package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.models.ElementConstitutif;
import com.example.demo.models.ElementConstitutifId;

public interface ElementConstitutifRepository extends JpaRepository<ElementConstitutif,ElementConstitutifId>{
	
	@Query("select ec from ElementConstitutif ec where ec.id = :id")
	ElementConstitutif findByElementConstitutifId(@Param("id") ElementConstitutifId id);
	

}
