package com.college.scrims.repository;

import com.college.scrims.model.Report;
import com.college.scrims.model.CR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByCr(CR cr);
    long countByStatus(String status);
}