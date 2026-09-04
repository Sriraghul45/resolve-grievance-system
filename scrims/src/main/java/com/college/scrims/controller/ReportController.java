package com.college.scrims.controller;

import com.college.scrims.model.Report;
import com.college.scrims.model.CR;
import com.college.scrims.repository.ReportRepository;
import com.college.scrims.repository.CRRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ReportController {

    @Autowired
    private ReportRepository reportRepository;
    
    @Autowired
    private CRRepository crRepository;

    @GetMapping("/")
    public String homePage() {
        return "login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/rep-dashboard")
    public String repDashboard(HttpSession session, Model model) {
        CR cr = (CR) session.getAttribute("loggedInCR");
        if (cr == null) {
            return "redirect:/login";
        }
        
        List<Report> reports = reportRepository.findByCr(cr);
        
        // Count only responses where admin responded AND CR has NOT seen it yet!
        long updatedCount = reports.stream()
                                   .filter(r -> r.getAdminResponse() != null && !r.getAdminResponse().isEmpty() && !r.isSeen())
                                   .count();
        
        model.addAttribute("cr", cr);
        model.addAttribute("reports", reports);
        model.addAttribute("updatedCount", updatedCount);
        return "rep-dashboard";
    }

    @GetMapping("/report/new")
    public String showReportForm(HttpSession session, Model model) {
        CR cr = (CR) session.getAttribute("loggedInCR");
        if (cr == null) return "redirect:/login";
        model.addAttribute("cr", cr);
        return "report-form"; 
    }

    @PostMapping("/report/save")
    public String saveReport(@RequestParam String description, 
                             @RequestParam String priority, 
                             HttpSession session) {
        CR cr = (CR) session.getAttribute("loggedInCR");
        if (cr == null) return "redirect:/login";

        Report report = new Report();
        report.setDescription(description);
        report.setPriority(priority);
        report.setStatus("Pending");
        report.setSeen(true); // New report has no admin response yet, so seen is true by default
        report.setCr(cr);

        reportRepository.save(report);
        return "redirect:/report/status";
    }

    @GetMapping("/report/status")
    public String showStatusPage(HttpSession session, Model model) {
        CR cr = (CR) session.getAttribute("loggedInCR");
        if (cr == null) return "redirect:/login";
        
        List<Report> reports = reportRepository.findByCr(cr);
        
        // CR has visited View Status, so mark all current admin-responded reports as SEEN!
        boolean changesMade = false;
        for (Report r : reports) {
            if (r.getAdminResponse() != null && !r.getAdminResponse().isEmpty() && !r.isSeen()) {
                r.setSeen(true);
                reportRepository.save(r);
                changesMade = true;
            }
        }
        
        model.addAttribute("reports", reports);
        model.addAttribute("cr", cr);
        return "view-status";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        if (session.getAttribute("loggedInAdmin") == null) return "redirect:/login";
        
        List<Report> reports = reportRepository.findAll();
        model.addAttribute("reports", reports);
        return "admin-dashboard";
    }

    @PostMapping("/admin/respond")
    public String updateReportStatus(@RequestParam Long id, 
                                     @RequestParam String adminResponse, 
                                     @RequestParam String status,
                                     HttpSession session) {
        if (session.getAttribute("loggedInAdmin") == null) return "redirect:/login";

        Report report = reportRepository.findById(id).orElse(null);
        if (report != null) {
            report.setAdminResponse(adminResponse);
            report.setStatus(status);
            report.setSeen(false); // Mark as unseen so CR gets notification!
            reportRepository.save(report);
        }
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/export/pdf")
    public String exportPdf(HttpSession session, Model model) {
        if (session.getAttribute("loggedInAdmin") == null) return "redirect:/login";

        List<Report> reports = reportRepository.findAll();
        model.addAttribute("reports", reports);
        model.addAttribute("totalCount", reports.size());
        model.addAttribute("pendingCount", reportRepository.countByStatus("Pending"));
        model.addAttribute("inProgressCount", reportRepository.countByStatus("In Progress"));
        model.addAttribute("resolvedCount", reportRepository.countByStatus("Resolved"));
        
        return "admin-pdf-view";
    }
}