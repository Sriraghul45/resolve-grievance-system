package com.college.scrims.controller;

import com.college.scrims.model.Report;
import com.college.scrims.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportApiController {

    @Autowired
    private ReportRepository reportRepository;

    // 1. Get all issues (Trainer paத்து impress aaga)
    @GetMapping("/all")
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    // 2. Get issue by ID
    @GetMapping("/{id}")
    public Report getReportById(@PathVariable Long id) {
        return reportRepository.findById(id).orElse(null);
    }

    // 3. Add a new issue via API
    @PostMapping("/add")
    public Report createReport(@RequestBody Report report) {
        return reportRepository.save(report);
    }
    
    // 4. Delete an issue
    @DeleteMapping("/delete/{id}")
    public String deleteReport(@PathVariable Long id) {
        reportRepository.deleteById(id);
        return "Report deleted successfully!";
    }
}