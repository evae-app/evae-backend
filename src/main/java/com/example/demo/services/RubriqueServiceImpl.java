package com.example.demo.services;

import com.example.demo.DTO.RubriqueDTO;
import com.example.demo.exception.DuplicateEntityException;
import com.example.demo.exception.NotFoundEntityException;
import com.example.demo.exception.UsedEntityException;
import com.example.demo.models.Rubrique;
import com.example.demo.repositories.RubriqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RubriqueServiceImpl implements RubriqueService{
    @Autowired
    private RubriqueRepository rubriqueRepository;

    @Override
    public List<RubriqueDTO> getAllRubriques() {
        List<Rubrique> questions = rubriqueRepository.findAll();
        List<RubriqueDTO> RubriqueDTOs = new ArrayList<>();

        for (Rubrique rubrique : questions) {
            RubriqueDTO RubriqueDTO = new RubriqueDTO();

            RubriqueDTO.setDesignation(rubrique.getDesignation());
            RubriqueDTO.setOrdre(rubrique.getOrdre());
            RubriqueDTO.setType(rubrique.getType());
            RubriqueDTO.setId(rubrique.getId());
            RubriqueDTOs.add(RubriqueDTO);
        }

        return RubriqueDTOs;
    }
    @Override
    public Rubrique getRubriqueById(Integer id) {
        return rubriqueRepository.findById(id).orElse(null);
    }

    @Override
    public Rubrique createRubrique(Rubrique rubrique) {
        /*if (rubrique.getNoEnseignant() == null) {
            rubrique.setType("RBS");
        } else {
            rubrique.setType("RBP");
        }*/
        if (rubriqueRepository.existsByDesignation(rubrique.getDesignation())) {
            throw new DuplicateEntityException(" rubrique");
        }
        Long maxOrdre = rubriqueRepository.findMaxOrdre();

        rubrique.setOrdre((maxOrdre != null) ? maxOrdre + 1 : 1);
        return rubriqueRepository.save(rubrique);
    }

    @Override
    public Rubrique updateRubrique(Integer id, Rubrique rubrique) {
        if (rubriqueRepository.existsById(id)) {
            Rubrique existingRubrique = rubriqueRepository.findById(id).orElse(null);

            if (rubriqueRepository.existsByDesignationAndIdNot(rubrique.getDesignation(), id)) {
                throw new DuplicateEntityException("rubrique");
            }

            if (isRubriqueUsedInEvaluation(existingRubrique)) {
                throw new UsedEntityException("rubrique");
            }

            rubrique.setId(id);

            /*if (rubrique.getNoEnseignant() == null) {
                rubrique.setType("RBS");
            } else {
                rubrique.setType("RBP");
            }*/
            return rubriqueRepository.save(rubrique);
        } else {
            throw new NotFoundEntityException("rubrique");
        }
    }




    public Rubrique updateRubriqueByDesignation(String designation, Rubrique updatedRubrique) {
        Optional<Rubrique> optionalRubrique = rubriqueRepository.findByDesignation(designation);

        if (optionalRubrique.isPresent()) {
            Rubrique existingRubrique = optionalRubrique.get();


            existingRubrique.setType(updatedRubrique.getType());
            existingRubrique.setNoEnseignant(updatedRubrique.getNoEnseignant());
            existingRubrique.setDesignation(updatedRubrique.getDesignation());
            existingRubrique.setOrdre(updatedRubrique.getOrdre());

            return rubriqueRepository.save(existingRubrique);
        } else {
            throw new NotFoundEntityException("rubrique");
        }
    }

    @Override
    public void deleteRubrique(Integer id) {
        if (rubriqueRepository.existsById(id)) {
            Rubrique existingRubrique = rubriqueRepository.findById(id).orElse(null);
            if (isRubriqueUsedInEvaluation(existingRubrique)) {
                throw new UsedEntityException("rubrique");
            }
            rubriqueRepository.deleteById(id);
        } else {
            throw new NotFoundEntityException("rubrique");
        }
    }

    @Override
    public void deleteRubriqueByDesignation(String designation) {
        Optional<Rubrique> optionalRubrique = rubriqueRepository.findByDesignation(designation);
        if (optionalRubrique.isPresent()) {
            Integer id = optionalRubrique.get().getId();
            Rubrique existingRubrique = rubriqueRepository.findById(id).orElse(null);
            if (isRubriqueUsedInEvaluation(existingRubrique)) {
                throw new UsedEntityException("rubrique");
            }
                rubriqueRepository.deleteById(id);
        } else {
            throw new NotFoundEntityException("rubrique");
        }
    }


    private boolean isRubriqueUsedInEvaluation(Rubrique rubrique) {
        // Vérifier si rubrique est null
        if (rubrique == null) {
            return false; // Ou lancez une exception si nécessaire
        }
        // Utiliser la méthode isEmpty() sur la liste uniquement si elle n'est pas null
        return rubrique.getRubriqueEvaluations() != null && !rubrique.getRubriqueEvaluations().isEmpty() ||
                rubrique.getRubriqueQuestions() != null && !rubrique.getRubriqueQuestions().isEmpty();
    }


    @Override
    public Optional<Rubrique> findRubriqueByDesignation(String designation) {
        return rubriqueRepository.findByDesignation(designation);
    }

    @Override
    public void updateOrdre(List<Rubrique> updatedRubriquesData) {
        rubriqueRepository.saveAll(updatedRubriquesData);
    }
}
