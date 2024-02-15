package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Authentification;

@Repository
public interface UserRepository extends JpaRepository<Authentification, Long>{

	@Query("select u from Authentification u where u.loginConnection = :loginConnection")
	Authentification findByEmail(@Param("loginConnection") String loginConnection);

	@Query("select u from Authentification u where u.id = :idUser")
	Authentification findById(@Param("idUser") long idUser);


}