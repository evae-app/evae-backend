package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.models.Enseignant;


public interface EnseignantRepository extends JpaRepository<Enseignant,Short>{


    Optional<Enseignant> findById(Short id);

}