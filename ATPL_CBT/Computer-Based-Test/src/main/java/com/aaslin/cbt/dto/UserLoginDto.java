package com.aaslin.cbt.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserLoginDto {
	
	@NotBlank(message = "Email is required")
	@Email(message = "Please provide a valid email address")
	private String email;
	
	@NotBlank(message = "College UID is required")
	private String collegeUid;
	
	@NotBlank(message = "College Roll Number is required")
	private String collegeRollNo;
	
	// Getters and Setters
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
}
