package com.aaslin.cbt.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	@Id
	@Column(name = "user_id", length = 100)
	private String userId;

	@JsonProperty("email")
	@Column(name = "email", length = 255)
	private String email;

	@JsonProperty("collegeUid")
	@Column(name = "college_uid", length = 100)
	private String collegeUid;

	@JsonProperty("collegeRollNo")
	@Column(name = "college_roll_no", length = 100)
	private String collegeRollNo;

	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	private Role role = Role.USER;

	// Relationships
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Submissions> submissions;
	
	@OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
	@JsonIgnore
	@JsonManagedReference
	private List<Contest> createdContests;
	
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
