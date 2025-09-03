package com.aaslin.cbt.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;

@Entity
public class User {
	
	private Long userId;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCollegeId() {
		return collegeId;
	}
	public void setCollegeId(String collegeId) {
		this.collegeId = collegeId;
	}
	public String getCollegeRollNo() {
		return CollegeRollNo;
	}
	public void setCollegeRollNo(String collegeRollNo) {
		CollegeRollNo = collegeRollNo;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	private String email;
	private String collegeId;
	private String CollegeRollNo;
	@Enumerated(EnumType.STRING)
	@Column(name="role")
	private Role role;
	public enum Role{
		User,
		Admin
	}
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Submissions> submissions;
}
