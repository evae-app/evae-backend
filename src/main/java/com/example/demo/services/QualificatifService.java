package com.example.demo.services;


import com.example.demo.exception.QualificatifAlreadyExistsException;
import com.example.demo.exception.QualificatifInUseException;
import com.example.demo.exception.QualificatifNotFoundException;
import com.example.demo.models.Qualificatif;
import com.example.demo.models.Question;
import com.example.demo.repositories.QualificatifRepository;
import com.example.demo.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QualificatifService {

    @Autowired
    private QualificatifRepository qualificatifRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public Qualificatif saveQualificatif(Qualificatif qualificatif) {
        String maximal = qualificatif.getMaximal();
        String minimal = qualificatif.getMinimal();

        // Check if a Qualificatif with the same maximal or minimal value already exists
        if (qualificatifRepository.existsByMaximalOrMinimal(maximal, minimal)) {
            throw new QualificatifAlreadyExistsException(maximal, minimal);
        }

        return qualificatifRepository.save(qualificatif);
    }

    public List<Qualificatif> getQualificatifs(){
        return qualificatifRepository.findAll();
    }

    /*
    public Qualificatif getQualificatifById(int id){
        return qualificatifRepository.findById(id).orElse(null);
    }  */

    public Qualificatif getQualificatifById(int id) {
        return qualificatifRepository.findById(id).orElseThrow(() -> new QualificatifNotFoundException(id));
    }


    public String deleteQualificatif(int id){

        if (!qualificatifRepository.existsById(id)) {
            throw new QualificatifNotFoundException(id);
        }

        qualificatifRepository.deleteById(id);
        return "Qualificatif supprime || " + id;


    }


    /*
    public String deleteQualificatif(int id){
        Qualificatif existingQualificatif = qualificatifRepository.findById(id).orElseThrow(() -> new QualificatifNotFoundException(id));

        // Check if the Qualificatif is used in any Question
        List<Question> questionsWithQualificatif = questionRepository.findByQualificatif(existingQualificatif);
        if (!questionsWithQualificatif.isEmpty()) {
            throw new QualificatifInUseException(id);
        }


        qualificatifRepository.delete(existingQualificatif);

        return "Qualificatif deleted || " + id;
    }

    */


    /*
    public Qualificatif updateQualificatif(Qualificatif qualificatif){
        Qualificatif existingQualificatif = qualificatifRepository.findById(qualificatif.getId()).orElse(null);
        existingQualificatif.setMaximal(qualificatif.getMaximal());
        existingQualificatif.setMinimal(qualificatif.getMinimal());
        return qualificatifRepository.save(existingQualificatif);
    }
    */

    public Qualificatif updateQualificatif(Qualificatif qualificatif){
        Qualificatif existingQualificatif = qualificatifRepository.findById(qualificatif.getId())
                .orElseThrow(() -> new QualificatifNotFoundException(qualificatif.getId()));



        if (qualificatifRepository.existsByMaximalOrMinimal(qualificatif.getMaximal(), qualificatif.getMinimal())) {
            throw new QualificatifAlreadyExistsException(qualificatif.getMaximal(), qualificatif.getMinimal());
        }

        if (qualificatifRepository.existsByMaximal(qualificatif.getMinimal()) ||
                qualificatifRepository.existsByMinimal(qualificatif.getMaximal())) {
            throw new QualificatifAlreadyExistsException(qualificatif.getMaximal(), qualificatif.getMinimal());
        }

        existingQualificatif.setMaximal(qualificatif.getMaximal());
        existingQualificatif.setMinimal(qualificatif.getMinimal());
        return qualificatifRepository.save(existingQualificatif);
    }

}