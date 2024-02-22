package com.example.demo.services;

import com.example.demo.DTO.RubriqueDTO;
import com.example.demo.models.Rubrique;

import java.util.List;
import java.util.Optional;

public interface RubriqueService {
    List<RubriqueDTO> getAllRubriques();
    Rubrique getRubriqueById(Integer id);
    Rubrique createRubrique(Rubrique rubrique);
    Rubrique updateRubrique(Integer id, Rubrique rubrique);

    Rubrique updateRubriqueByDesignation(String designation, Rubrique rubrique);
    void deleteRubrique(Integer id);

    void deleteRubriqueByDesignation(String designation);
    Optional<Rubrique> findRubriqueByDesignation(String designation);

    void updateOrdre(List<Rubrique> updatedRubriquesData);
}
