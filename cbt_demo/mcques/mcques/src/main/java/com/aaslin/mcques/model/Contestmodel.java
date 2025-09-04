package com.aaslin.mcques.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
public class Contestmodel {

	
	private String contest_id;
	private String allowed_college_id;
	private String contest_name;
	private String created_by;
	private String duration;
	private String end_time;
	private String start_time;
	private String status;
	private String total_coding_questions;
	private String total_mcq_questions;
	
}
