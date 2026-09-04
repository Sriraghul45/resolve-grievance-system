package com.college.scrims.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "cr")
public class CR {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String department;
    private int studyYear;
    private String section;
    private String password;

    @OneToMany(mappedBy = "cr", cascade = CascadeType.ALL)
    private List<Report> reports;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public int getStudyYear() { return studyYear; }
    public void setStudyYear(int studyYear) { this.studyYear = studyYear; }

    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<Report> getReports() { return reports; }
    public void setReports(List<Report> reports) { this.reports = reports; }
}