package com.aaslin.cbt.participant.service;

import org.springframework.stereotype.Service;

import com.aaslin.cbt.common.model.Designation;
import com.aaslin.cbt.participant.repository.DesignationRepository;

import jakarta.transaction.Transactional;

@Service
public class DesignationService {

	private final DesignationRepository designationRepo;

	public DesignationService(DesignationRepository designationRepo) {
		super();
		this.designationRepo = designationRepo;
	}
	
	@Transactional
	public Designation createOrGetName(String designationName) {
		return designationRepo.findByDesignationName(designationName).orElseGet(()-> {
			long count=designationRepo.count()+1;
			String newId="D"+String.format("%03d",count);
			
			Designation newDesignation=new Designation();
			newDesignation.setDesignationId(newId);
			newDesignation.setDesignationName(designationName);
			return designationRepo.save(newDesignation);
			
		});
		
	}
	
}
