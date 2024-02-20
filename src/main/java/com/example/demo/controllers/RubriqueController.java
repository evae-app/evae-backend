package com.example.demo.controllers;

import com.example.demo.models.Rubrique;
import com.example.demo.services.RubriqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rubriques")
public class RubriqueController {
    @Autowired
    private RubriqueService rubriqueService;

    @GetMapping
    public List<Rubrique> getAllRubriques() {
        return rubriqueService.getAllRubriques();
    }

    @GetMapping("/{id}")
    public Rubrique getRubriqueById(@PathVariable Integer id) {
        return rubriqueService.getRubriqueById(id);
    }
    @GetMapping("/designation/{designation}")
    public ResponseEntity<Rubrique> getRubriqueByDesignation(@PathVariable String designation) {
        Optional<Rubrique> rubrique = rubriqueService.findRubriqueByDesignation(designation);
        return rubrique.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Rubrique> createRubrique(@RequestBody Rubrique rubrique) {
        Rubrique createdRubrique = rubriqueService.createRubrique(rubrique);
        return new ResponseEntity<>(createdRubrique, HttpStatus.CREATED);
    }

    @PostMapping("/update/{id}")
    public Rubrique updateRubrique(@PathVariable Integer id, @RequestBody Rubrique rubrique) {
        return rubriqueService.updateRubrique(id, rubrique);
    }
    @PostMapping("/update/designation/{designation}")
    public Rubrique updateRubriqueByDesignation(@PathVariable String designation, @RequestBody Rubrique rubrique) {
        return rubriqueService.updateRubriqueByDesignation(designation, rubrique);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRubrique(@PathVariable Integer id) {
        rubriqueService.deleteRubrique(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/delete/designation/{designation}")
    public ResponseEntity<Void> deleteRubriqueByDesignation(@PathVariable String designation) {
        rubriqueService.deleteRubriqueByDesignation(designation);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
