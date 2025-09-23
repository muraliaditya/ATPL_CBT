package com.aaslin.cbt.participant.service;

import java.util.List;
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
import com.aaslin.cbt.participant.security.JwtUtil;

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

	    String categoryName = request.getCategoryName().toLowerCase();
	    List<Category> categoryNames=categoryRepo.findByCategoryName(categoryName);
	    Category category;
        if(categoryNames.isEmpty()) {
        	long count=categoryRepo.count()+1;
        	String newId="CAT"+String.format("%03d", count);
        	Category newCategory=new Category();
        	newCategory.setCategoryId(newId);
        	newCategory.setCategoryName(request.getCategoryName());
        	category=categoryRepo.save(newCategory);
        }
        else {
        	category=categoryNames.get(0);
        }

	    switch (categoryName) {
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
	    long participant_count=participantRepo.count()+1;
    	String newParticipantId="PAR"+String.format("%03d", participant_count);
	    participant.setParticipantId(newParticipantId);
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
	        List<Company> companies = companyRepo.findBycurrentCompanyName(request.getCurrentCompanyName());
	        Company company;
	        if(companies.isEmpty()) {
	        	long count=companyRepo.count()+1;
	        	String newId="COM"+String.format("%03d", count);
	        	Company newCompany=new Company();
	        	newCompany.setCompanyId(newId);
	        	newCompany.setCurrentCompanyName(request.getCurrentCompanyName());
	        	company=companyRepo.save(newCompany);
	        }
	        else {
	        	company=companies.get(0);
	        }
	        
	        participant.setCompany(company);
	    }

	    if (request.getCollegeName() != null) {
	    	List<College> colleges =collegeRepo.findByCollegeName(request.getCollegeName());
	        College college;
	        if(colleges.isEmpty()) {
	        	long count=collegeRepo.count()+1;
	        	String newId="COL"+String.format("%03d", count);
	        	College newCollege=new College();
	        	newCollege.setCollegeId(newId);
	        	newCollege.setCollegeName(request.getCollegeName());
	        	college=collegeRepo.save(newCollege);
	        }
	        else {
	        	college=colleges.get(0);
	        }
	        participant.setCollege(college);
	    }

	    participant.setContest(contest);

	    participant = participantRepo.save(participant);

	    String token=JwtUtil.generateToken(participant.getParticipantId().toString(), contest.getContestId());
	    return new ParticipantResponse(participant.getParticipantId(),token);
	}
	
	
}
