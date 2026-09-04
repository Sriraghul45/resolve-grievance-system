package com.college.scrims.model;

import jakarta.persistence.*;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String description;

    private String priority; // Category
    private String status;   // Pending, In Progress, Resolved
    
    @Column(length = 1000)
    private String adminResponse;

    private boolean seen = false; // To track if CR has viewed the admin response

    @ManyToOne
    @JoinColumn(name = "cr_id")
    private CR cr;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getAdminResponse() { return adminResponse; }
    public void setAdminResponse(byte[] adminResponse) { /* if byte array or string */ }
    public void setAdminResponse(String adminResponse) { 
        this.adminResponse = adminResponse;
        this.seen = false; // Whenever admin updates, mark as unseen for CR
    }

    public boolean isSeen() { return seen; }
    public void setSeen(boolean seen) { this.seen = seen; }

    public CR getCr() { return cr; }
    public void setCr(CR cr) { this.cr = cr; }
}