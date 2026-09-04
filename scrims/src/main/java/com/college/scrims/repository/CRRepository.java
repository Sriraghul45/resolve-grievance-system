package com.college.scrims.repository;

import com.college.scrims.model.CR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CRRepository extends JpaRepository<CR, Long> {
    
    @Query("SELECT c FROM CR c WHERE c.name = :name AND c.department = :department AND c.studyYear = :studyYear AND c.section = :section")
    CR findByDetails(@Param("name") String name, 
                     @Param("department") String department, 
                     @Param("studyYear") int studyYear, 
                     @Param("section") String section);

    CR findByName(String name);
}