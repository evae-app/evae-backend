package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.EvaluationDTO;
import com.example.demo.services.EvaluationService;

@RestController
@RequestMapping("/api/v1/evaluation")
public class EvaluationController {
	
	@Autowired
	EvaluationService evaluationsservice;
	
	@GetMapping
	public ResponseEntity<List<EvaluationDTO>> getEvaluations(){
		try {
			return evaluationsservice.getEvaluations();
			
		} catch(Exception e){
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
