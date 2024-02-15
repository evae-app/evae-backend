package com.example.demo.repositories;

import com.example.demo.models.Rubrique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RubriqueRepository extends JpaRepository<Rubrique, Integer> {
    boolean existsByDesignation(String designation);
    boolean existsByDesignationAndIdNot(String designation, Integer id);
    Optional<Rubrique> findByDesignation(String designation);
    void deleteByDesignation(String designation);
}
