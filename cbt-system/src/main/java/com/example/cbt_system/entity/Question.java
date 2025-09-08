package com.example.cbt_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Question {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String title;
	@Column(length=2000)
	private String description;
	private String level;
	private String status;
	
	@Column(length=2000)
	private String inputFormat;
	
	@Column(length=2000)
	private String outputFormat;
	
	@Column(length=2000)
	private String sampleInput;
	
	@Column(length=2000)
	private String sampleOutput;
	private String createdBy;
	
}
