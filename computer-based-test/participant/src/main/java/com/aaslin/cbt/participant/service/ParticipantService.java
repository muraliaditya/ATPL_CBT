package com.aaslin.cbt.participant.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.aaslin.cbt.common.model.Company;
import com.aaslin.cbt.common.model.Contest;
import com.aaslin.cbt.common.model.Designation;
import com.aaslin.cbt.common.model.Participant;
import com.aaslin.cbt.participant.dto.ParticipantRequest;
import com.aaslin.cbt.participant.dto.ParticipantResponse;
import com.aaslin.cbt.participant.repository.CompanyRepository;
import com.aaslin.cbt.participant.repository.ContestRepository;
import com.aaslin.cbt.participant.repository.DesignationRepository;
import com.aaslin.cbt.participant.repository.ParticipantRepository;

@Service
public class ParticipantService {

	private final ParticipantRepository participantRepo;
	private final ContestRepository contestRepo;
	private final CompanyRepository companyRepo;
	private final DesignationRepository designationRepo;
	

	public ParticipantService(ParticipantRepository participantRepo, ContestRepository contestRepo,
			CompanyRepository companyRepo, DesignationRepository designationRepo) {
		super();
		this.participantRepo = participantRepo;
		this.contestRepo = contestRepo;
		this.companyRepo = companyRepo;
		this.designationRepo = designationRepo;
	}


	public ParticipantResponse registerParticipant(String contestId,ParticipantRequest request) {
		Contest contest=contestRepo.findById(contestId).orElseThrow(()->new RuntimeException("Contest not found"));
		String category=contest.getCategory().getCategoryName().toLowerCase();
		switch(category) {
		case "student" :
			if(request.getCollegeRegdNo() == null || request.getCollege() ==null) {
				throw new RuntimeException("Missing  student details");
			}
			break;
		case "experienced" :
			if(request.getCompany() == null || request.getOverallExperience() == null) {
				throw new RuntimeException("Missing details");
			}
			break;
			
			default :
				throw new RuntimeException("unsupported catgeory"+category);
		}
		Participant participant=new Participant();
		participant.setParticipantId(request.getParticipantId());
		participant.setName(request.getName());
		participant.setEmail(request.getEmail());
		participant.setCollegeRegdNo(request.getCollegeRegdNo());
		participant.setCollege(request.getCollege());
		participant.setHighestDegree(request.getHighestDegree());
		participant.setOverallExperience(request.getOverallExperience());
		Designation designation = designationRepo.findByDesignationName(request.getDesignationName())
	            .orElseGet(() -> {
	                Designation newDesignation = new Designation();
	                newDesignation.setDesignationName(request.getDesignationName());
	                return designationRepo.save(newDesignation); // save new one
	            });
	    participant.setDesignation(designation);
	    Company company = companyRepo.findBycurrentCompanyName(request.getCompany().getCurrentCompanyName())
	            .orElseGet(() -> {
	                Company newCompany = new Company();
	                newCompany.setCurrentCompanyName(request.getCompany().getCurrentCompanyName());
	                return companyRepo.save(newCompany);
	            });
	    participant.setCompany(company);

		participant.setContest(contest);
		
		participant=participantRepo.save(participant);
		
		String accessToken=UUID.randomUUID().toString();
		
		return new ParticipantResponse(participant.getParticipantId(),accessToken);
		
	}
	
	
}
