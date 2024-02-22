package com.example.demo.repositories;

import com.example.demo.models.Qualificatif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QualificatifRepository extends JpaRepository<Qualificatif, Integer> {

    boolean existsByMaximalAndIdNot(String maximal, Integer id);

    boolean existsByMinimalAndIdNot(String minimal,Integer id);

    @Query("SELECT CASE WHEN COUNT(q) > 0 THEN true ELSE false END FROM Qualificatif q WHERE q.maximal = :maximal OR q.minimal = :minimal")
    boolean existsByMaximalOrMinimal(@Param("maximal") String maximal, @Param("minimal") String minimal);

    boolean existsByMaximal(String maximal);
    boolean existsByMinimal(String minimal);

    @Query("select qf from Qualificatif qf where qf.minimal = :minimal")
    Qualificatif findByMinmal(@Param("minimal") String minimal);

    @Query("select qf from Qualificatif qf where qf.maximal = :maximal")
    Qualificatif findByMaxmal(@Param("maximal") String maximal);

}
