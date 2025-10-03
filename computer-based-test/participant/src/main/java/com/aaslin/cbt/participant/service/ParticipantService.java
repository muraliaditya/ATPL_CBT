package com.aaslin.cbt.participant.service;
/**
 * 
 */
import java.util.List;

import org.springframework.stereotype.Service;

import com.aaslin.cbt.common.model.*;
import com.aaslin.cbt.participant.dto.ParticipantRequest;
import com.aaslin.cbt.participant.dto.ParticipantResponse;
import com.aaslin.cbt.participant.exception.CustomException;
import com.aaslin.cbt.participant.mapper.ParticipantMapper;
import com.aaslin.cbt.participant.repository.*;
import com.aaslin.cbt.participant.security.JwtUtil;

import jakarta.transaction.Transactional;

@Service
public class ParticipantService {

    private final DesignationService designationService;
    private final ParticipantRepository participantRepo;
    private final ContestRepository contestRepo;
    private final CompanyRepository companyRepo;
    private final CollegeRepository collegeRepo;
    private final CategoryRepository categoryRepo;

    public ParticipantService(DesignationService designationService,
                              ParticipantRepository participantRepo,
                              ContestRepository contestRepo,
                              CompanyRepository companyRepo,
                              CollegeRepository collegeRepo,
                              CategoryRepository categoryRepo) {
        this.designationService = designationService;
        this.participantRepo = participantRepo;
        this.contestRepo = contestRepo;
        this.companyRepo = companyRepo;
        this.collegeRepo = collegeRepo;
        this.categoryRepo = categoryRepo;
    }

    @Transactional
    public ParticipantResponse registerParticipant(String contestId, ParticipantRequest request) {
        try {
            Contest contest = contestRepo.findById(contestId)
                    .orElseThrow(() -> new CustomException.ContestNotFoundException("Contest not found"));

            Category category = getOrCreateCategory(request.getCategoryName());

            Participant participant = ParticipantMapper.toParticipantEntity(request);
            participant.setParticipantId(generateParticipantId());
            participant.setContest(contest);

            if (request.getDesignationName() != null) {
                participant.setDesignation(designationService.createOrGetName(request.getDesignationName()));
            }

            if (request.getCurrentCompanyName() != null) {
                participant.setCompany(getOrCreateCompany(request.getCurrentCompanyName()));
            }

            if (request.getCollegeName() != null) {
                participant.setCollege(getOrCreateCollege(request.getCollegeName()));
            }

            participantRepo.save(participant);

            String token = JwtUtil.generateToken(participant.getParticipantId(), contest.getContestId());
            return new ParticipantResponse(participant.getParticipantId(), token);

        } catch (CustomException.ContestNotFoundException |
                 CustomException.ParticipantValidationException |
                 CustomException.UnsupportedCategoryException ex) {
            throw ex;
        } catch (Exception exception) {
            throw new CustomException.ParticipantValidationException("Error registering participant: " + exception.getMessage());
        }
    }

    private Category getOrCreateCategory(String categoryName) {
        try {
            String lowerName = categoryName.toLowerCase();
            List<Category> categories = categoryRepo.findByCategoryName(lowerName);
            if (categories.isEmpty()) {
                long count = categoryRepo.count() + 1;
                String newId = "CAT" + String.format("%03d", count);
                Category newCategory = new Category();
                newCategory.setCategoryId(newId);
                newCategory.setCategoryName(categoryName);
                return categoryRepo.save(newCategory);
            } else {
                return categories.get(0);
            }
        } catch (Exception ex) {
            throw new CustomException.ParticipantValidationException("Error creating/finding category ");
        }
    }

    private void validateCategory(String categoryName, ParticipantRequest request) {
        try {
            switch (categoryName.toLowerCase()) {
                case "student":
                    if (request.getCollegeName() == null || request.getCollegeRegdNo() == null)
                        throw new CustomException.ParticipantValidationException("Missing student details");
                    break;
                case "experienced":
                    if (request.getCurrentCompanyName() == null || request.getOverallExperience() == null)
                        throw new CustomException.ParticipantValidationException("Missing experienced details");
                    break;
                default:
                    throw new CustomException.UnsupportedCategoryException("Unsupported category: " + categoryName);
            }
        } catch (Exception categoryException) {
            throw new CustomException.ParticipantValidationException("Category validation error: " + categoryException.getMessage());
        }
    }

    private Company getOrCreateCompany(String name) {
        try {
            return companyRepo.findBycurrentCompanyName(name)
                    .stream().findFirst()
                    .orElseGet(() -> {
                        long count = companyRepo.count() + 1;
                        Company company = new Company();
                        company.setCompanyId("COM" + String.format("%03d", count));
                        company.setCurrentCompanyName(name);
                        return companyRepo.save(company);
                    });
        } catch (Exception ex) {
            throw new CustomException.ParticipantValidationException("Error creating company: " + ex.getMessage());
        }
    }

    private College getOrCreateCollege(String name) {
        try {
            return collegeRepo.findByCollegeName(name)
                    .stream().findFirst()
                    .orElseGet(() -> {
                        long count = collegeRepo.count() + 1;
                        College college = new College();
                        college.setCollegeId("COL" + String.format("%03d", count));
                        college.setCollegeName(name);
                        return collegeRepo.save(college);
                    });
        } catch (Exception ex) {
            throw new CustomException.ParticipantValidationException("Error creating college: " + ex.getMessage());
        }
    }

    private String generateParticipantId() {
        try {
            long count = participantRepo.count() + 1;
            return "PAR" + String.format("%03d", count);
        } catch (Exception ex) {
            throw new CustomException.ParticipantValidationException("Error generating participant ID: " + ex.getMessage());
        }
    }
}