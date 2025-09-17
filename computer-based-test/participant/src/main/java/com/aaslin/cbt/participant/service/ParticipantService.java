package com.aaslin.cbt.participant.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.aaslin.cbt.common.model.Category;
import com.aaslin.cbt.common.model.College;
import com.aaslin.cbt.common.model.Company;
import com.aaslin.cbt.common.model.Contest;
import com.aaslin.cbt.common.model.Designation;
import com.aaslin.cbt.common.model.Participant;
import com.aaslin.cbt.participant.dto.ParticipantRequest;
import com.aaslin.cbt.participant.dto.ParticipantResponse;
import com.aaslin.cbt.participant.repository.CategoryRepository;
import com.aaslin.cbt.participant.repository.CollegeRepository;
import com.aaslin.cbt.participant.repository.CompanyRepository;
import com.aaslin.cbt.participant.repository.ContestRepository;
import com.aaslin.cbt.participant.repository.DesignationRepository;
import com.aaslin.cbt.participant.repository.ParticipantRepository;

@Service
public class ParticipantService {

	private final DesignationService designationService;
	private final ParticipantRepository participantRepo;
	private final ContestRepository contestRepo;
	private final CompanyRepository companyRepo;
	private final DesignationRepository designationRepo;
	private final CollegeRepository collegeRepo;
	private final CategoryRepository categoryRepo;
	



	public ParticipantService(DesignationService designationService, ParticipantRepository participantRepo,
			ContestRepository contestRepo, CompanyRepository companyRepo, DesignationRepository designationRepo,
			CollegeRepository collegeRepo, CategoryRepository categoryRepo) {
		super();
		this.designationService = designationService;
		this.participantRepo = participantRepo;
		this.contestRepo = contestRepo;
		this.companyRepo = companyRepo;
		this.designationRepo = designationRepo;
		this.collegeRepo = collegeRepo;
		this.categoryRepo = categoryRepo;
	}




	public ParticipantResponse registerParticipant(String contestId, ParticipantRequest request) {
	    Contest contest = contestRepo.findById(contestId)
	            .orElseThrow(() -> new RuntimeException("Contest not found"));

	    String category = request.getCategoryName().toLowerCase();
	    Category categoryName=categoryRepo.findByCategoryName(category).orElseGet(()->{
	    	Category newCategory =new Category();
	    	newCategory.setCategoryName(category);
	    	return categoryRepo.save(newCategory);
	    });

	    switch (category) {
	        case "student":
	            if (request.getCollegeRegdNo() == null || request.getCollegeName() == null) {
	                throw new RuntimeException("Missing student details");
	            }
	            break;
	        case "experienced":
	            if (request.getCurrentCompanyName() == null || request.getOverallExperience() == null) {
	                throw new RuntimeException("Missing experienced details");
	            }
	            break;
	        default:
	            throw new RuntimeException("Unsupported category " + category);
	    }

	    Participant participant = new Participant();
	    participant.setParticipantId(request.getParticipantId());
	    participant.setName(request.getName());
	    participant.setEmail(request.getEmail());
	    participant.setCollegeRegdNo(request.getCollegeRegdNo());
	    participant.setHighestDegree(request.getHighestDegree());
	    participant.setOverallExperience(request.getOverallExperience());

	    if(request.getDesignationName() != null) {
	 Designation designation=designationService.createOrGetName(request.getDesignationName());
	    participant.setDesignation(designation);
	    }

	    if (request.getCurrentCompanyName() != null) {
	        Company company = companyRepo.findBycurrentCompanyName(request.getCurrentCompanyName())
	                .orElseGet(() -> {
	                    Company newCompany = new Company();
	                    long count=companyRepo.count()+1;
	                    String newId="C"+String.format("%03d", count);
	                    newCompany.setCompanyId(newId);
	                    newCompany.setCurrentCompanyName(request.getCurrentCompanyName());
	                    return companyRepo.save(newCompany);
	                });
	        participant.setCompany(company);
	    }

	    if (request.getCollegeName() != null) {
	        College college = collegeRepo.findByCollegeName(request.getCollegeName())
	                .orElseGet(() -> {
	                    College newCollege = new College();
	                    newCollege.setCollegeName(request.getCollegeName());
	                    return collegeRepo.save(newCollege);
	                });
	        participant.setCollege(college);
	    }

	    participant.setContest(contest);

	    participant = participantRepo.save(participant);

	    String accessToken = UUID.randomUUID().toString();

	    return new ParticipantResponse(participant.getParticipantId(), accessToken);
	}
	
	
}
