package com.example.demo.repositories;

import com.example.demo.models.Qualificatif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QualificatifRepository extends JpaRepository<Qualificatif, Integer> {

    boolean existsByMaximalAndIdNot(String maximal, Integer id);

    boolean existsByMinimalAndIdNot(String minimal,Integer id);

    boolean existsByMaximalOrMinimal(String maximal, String minimal);

    boolean existsByMaximal(String maximal);
    boolean existsByMinimal(String minimal);

    @Query("select qf from Qualificatif qf where qf.minimal = :minimal")
    Qualificatif findByMinmal(@Param("minimal") String minimal);


}
