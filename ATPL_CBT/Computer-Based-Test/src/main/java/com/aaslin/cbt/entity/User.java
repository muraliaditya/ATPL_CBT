package com.aaslin.cbt.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@Column(name = "user_id", length = 100)
	private String userId;
	
	@Column(name = "email", length = 255)
	private String email;
	
	@Column(name = "college_uid", length = 100)
	private String collegeUid;
	
	@Column(name = "college_roll_no", length = 100)
	private String collegeRollNo;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	private Role role = Role.USER;
	
	// Relationships
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Submissions> submissions;
	
	@OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
	private List<Contest> createdContests;
	
	// Getters and Setters
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getCollegeUid() {
		return collegeUid;
	}
	
	public void setCollegeUid(String collegeUid) {
		this.collegeUid = collegeUid;
	}
	
	public String getCollegeRollNo() {
		return collegeRollNo;
	}
	
	public void setCollegeRollNo(String collegeRollNo) {
		this.collegeRollNo = collegeRollNo;
	}
	
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	public List<Submissions> getSubmissions() {
		return submissions;
	}
	
	public void setSubmissions(List<Submissions> submissions) {
		this.submissions = submissions;
	}
	
	public List<Contest> getCreatedContests() {
		return createdContests;
	}
	
	public void setCreatedContests(List<Contest> createdContests) {
		this.createdContests = createdContests;
	}
	
	public enum Role {
		USER,
		ADMIN
	}
}
