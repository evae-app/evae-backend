package com.example.demo.services;

import com.example.demo.exception.DuplicateEntityException;
import com.example.demo.exception.NotFoundEntityException;
import com.example.demo.exception.UsedEntityException;
import com.example.demo.models.Rubrique;
import com.example.demo.repositories.RubriqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RubriqueServiceImpl implements RubriqueService{
    @Autowired
    private RubriqueRepository rubriqueRepository;

    @Override
    public List<Rubrique> getAllRubriques() {
        return rubriqueRepository.findAll();
    }

    @Override
    public Rubrique getRubriqueById(Integer id) {
        return rubriqueRepository.findById(id).orElse(null);
    }

    @Override
    public Rubrique createRubrique(Rubrique rubrique) {
        if (rubrique.getNoEnseignant() == null) {
            rubrique.setType("RBS");
        } else {
            rubrique.setType("RBP");
        }
        if (rubriqueRepository.existsByDesignation(rubrique.getDesignation())) {
            throw new DuplicateEntityException();
        }
        return rubriqueRepository.save(rubrique);
    }

    @Override
    public Rubrique updateRubrique(Integer id, Rubrique rubrique) {
        if (rubriqueRepository.existsById(id)) {
            Rubrique existingRubrique = rubriqueRepository.findById(id).orElse(null);
            if (rubriqueRepository.existsByDesignationAndIdNot(rubrique.getDesignation(), id)) {
                throw new DuplicateEntityException();
            }
            if (isRubriqueUsedInEvaluation(existingRubrique)) {
                throw new UsedEntityException();
            }
            rubrique.setId(id);
            if (rubrique.getNoEnseignant() == null) {
                rubrique.setType("RBS");
            } else {
                rubrique.setType("RBP");
            }
            return rubriqueRepository.save(rubrique);
        } else {
            throw new NotFoundEntityException();
        }
    }

    public Rubrique updateRubriqueByDesignation(String designation, Rubrique updatedRubrique) {
        Optional<Rubrique> optionalRubrique = rubriqueRepository.findByDesignation(designation);

        if (optionalRubrique.isPresent()) {
            Rubrique existingRubrique = optionalRubrique.get();
            Integer id = existingRubrique.getId();
            updateRubrique(id,updatedRubrique);
            return rubriqueRepository.save(existingRubrique);
        } else {
            throw new NotFoundEntityException();
        }
    }

    @Override
    public void deleteRubrique(Integer id) {
        if (rubriqueRepository.existsById(id)) {
            Rubrique existingRubrique = rubriqueRepository.findById(id).orElse(null);
            if (isRubriqueUsedInEvaluation(existingRubrique)) {
                throw new UsedEntityException();
            }
            rubriqueRepository.deleteById(id);
        } else {
            throw new NotFoundEntityException();
        }
    }

    @Override
    public void deleteRubriqueByDesignation(String designation) {
        Optional<Rubrique> optionalRubrique = rubriqueRepository.findByDesignation(designation);
        if (optionalRubrique.isPresent()) {
            Integer id = optionalRubrique.get().getId();
            Rubrique existingRubrique = rubriqueRepository.findById(id).orElse(null);
            if (isRubriqueUsedInEvaluation(existingRubrique)) {
                throw new UsedEntityException();
            }
                rubriqueRepository.deleteById(id);
        } else {
            throw new NotFoundEntityException();
        }
    }


    private boolean isRubriqueUsedInEvaluation(Rubrique rubrique) {
        return !rubrique.getRubriqueEvaluations().isEmpty() || !rubrique.getRubriqueQuestions().isEmpty();
    }


    @Override
    public Optional<Rubrique> findRubriqueByDesignation(String designation) {
        return rubriqueRepository.findByDesignation(designation);
    }
}
