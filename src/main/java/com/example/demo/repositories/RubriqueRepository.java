package com.example.demo.repositories;

import com.example.demo.models.Rubrique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;


@Repository
public interface RubriqueRepository extends JpaRepository<Rubrique, Integer> {
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Rubrique r WHERE r.designation = :designation")
    boolean existsByDesignation(@Param("designation") String designation);

    boolean existsByDesignationAndIdNot(String designation, Integer id);
    Optional<Rubrique> findByDesignation(String designation);
    void deleteByDesignation(String designation);
}
