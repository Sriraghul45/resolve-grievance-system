package com.college.scrims.controller;

import com.college.scrims.model.CR;
import com.college.scrims.repository.CRRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private CRRepository crRepository;

    @PostMapping("/login")
    public String login(@RequestParam String name,
                        @RequestParam String year,
                        @RequestParam String dept,
                        @RequestParam String section,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        
        // Name mattum vechu database-la thedurom (Details mismatch aagura problem varathu)
        CR cr = crRepository.findByName(name.trim());

        // Password-m name-m match aaguthanu paarkarom
        if (cr != null && cr.getPassword() != null && cr.getPassword().equals(password.trim())) {
            session.setAttribute("loggedInCR", cr);
            return "redirect:/rep-dashboard";
        } else {
            model.addAttribute("error", "Invalid Password or CR Details!");
            return "login";
        }
    }

    @PostMapping("/admin/login")
    public String adminLogin(@RequestParam String email, 
                             @RequestParam String password, 
                             HttpSession session, 
                             Model model) {
        if ("admin@institute.edu".equals(email) && "Admin@2026!#".equals(password)) {
            session.setAttribute("loggedInAdmin", "true");
            return "redirect:/admin/dashboard";
        }
        model.addAttribute("error", "Invalid Admin Credentials!");
        return "login";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}