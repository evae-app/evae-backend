package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.constants.EvaeBackendConstants;
import com.example.demo.services.UserService;
import com.example.demo.utils.BackendUtils;
import com.example.demo.DTO.UserDTO;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	UserService userservice;
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> getUsers(){
		try {
			return userservice.getUsers();
			
		} catch(Exception e){
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value = "/inscription")
	public ResponseEntity<String> inscriptionUser(@RequestBody Map<String, String> requestMap){
		try {
            return userservice.inscrireUtilisateur(requestMap);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return BackendUtils.getResponseEntity(EvaeBackendConstants.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value="login")
	public ResponseEntity<String> login(@RequestBody Map<String, String> requestMap) {
		try {
			return userservice.login(requestMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return BackendUtils.getResponseEntity(EvaeBackendConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping(value="/checkToken")
	public ResponseEntity<String> checkToken() {
		try {
			return userservice.checkToken();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return BackendUtils.getResponseEntity(EvaeBackendConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value="/changePassword")
	public ResponseEntity<String> changePassword(@RequestBody Map<String, String> requestMap) {
		try {
			return userservice.changePassword(requestMap);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return BackendUtils.getResponseEntity(EvaeBackendConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@PostMapping(value="/forgotPassword")
	public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> requestMap) {
		try {
			return userservice.forgotPassword(requestMap);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return BackendUtils.getResponseEntity(EvaeBackendConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping("/update")
    public ResponseEntity<String> modifierPlan( @RequestBody Map<String, String> requestMap) {
		try {
			return userservice.update(requestMap);
		}catch (Exception ex) {
            ex.printStackTrace();
        }
        return BackendUtils.getResponseEntity(EvaeBackendConstants.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/delete/{idUser}")
    public ResponseEntity<String> supprimerPlan(@PathVariable("idUser") int idUser) {
        try{
        	return userservice.deleteUser(idUser);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return BackendUtils.getResponseEntity(EvaeBackendConstants.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @GetMapping("/get-user-by-id")
    public ResponseEntity<UserDTO> getUserByToken() {
        try{
        	return userservice.getUserByToken();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
		return new ResponseEntity<UserDTO>(new UserDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
