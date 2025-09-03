package com.aaslin.computer_based_test.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String collegeId;

    @Column(nullable = false)
    private String collegeRollNo;
    
    public enum Role{
    	USER,ADMIN
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // USER or ADMIN

    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCollegeId() { return collegeId; }
    public void setCollegeId(String collegeId) { this.collegeId = collegeId; }

    public String getCollegeRollNo() { return collegeRollNo; }
    public void setCollegeRollNo(String collegeRollNo) { this.collegeRollNo = collegeRollNo; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
