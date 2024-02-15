package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Etudiant;

public interface EtudiantRepository extends JpaRepository<Etudiant, String>{

    Etudiant findByNoEtudiant(String noEtudiant);

}