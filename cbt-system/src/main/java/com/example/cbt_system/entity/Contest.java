package com.example.cbt_system.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Entity
@Data
public class Contest {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String description;
	private LocalDateTime startTime;
	private int durationMinutes;
	private LocalDateTime endTime;
	
	
	@ManyToMany
	@JoinTable(name="contest_questions",joinColumns=@JoinColumn(name="contest_id"),inverseJoinColumns=@JoinColumn(name="question_id"))
	private List<Question> questions=new ArrayList<>();
	
	@PrePersist
	@PreUpdate
	public void calculateEndTime() {
		if(startTime!=null && durationMinutes>0) {
			this.endTime=startTime.plusMinutes(durationMinutes);
		}
	}
}
