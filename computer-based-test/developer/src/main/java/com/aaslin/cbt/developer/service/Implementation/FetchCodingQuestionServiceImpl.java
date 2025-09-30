package com.aaslin.cbt.developer.service.Implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aaslin.cbt.common.model.CodingQuestions;
import com.aaslin.cbt.common.model.Testcases;
import com.aaslin.cbt.developer.Dto.FetchCodingQuestionDto;
import com.aaslin.cbt.developer.Dto.FetchTestcaseDto;
import com.aaslin.cbt.developer.repository.CodingQuestionRepository;
import com.aaslin.cbt.developer.repository.TestcaseRepository;
import com.aaslin.cbt.developer.service.FetchCodingQuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FetchCodingQuestionServiceImpl implements FetchCodingQuestionService{
	
	@Autowired
	private CodingQuestionRepository codingQuestionRepo;
	
	@Autowired
	private TestcaseRepository testcaseRepo;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Override
	public FetchCodingQuestionDto getQuestionById(String questionId) {
		
		CodingQuestions question = codingQuestionRepo.
				findByCodingQuestionIdAndApprovalStatus(questionId, CodingQuestions.ApprovalStatus.APPROVED)
				.orElseThrow(()-> new RuntimeException("Question not found or not approved"));
	    
	    List<Testcases> testcases = testcaseRepo.findByCodingQuestion_CodingQuestionIdAndTestcaseType(questionId,Testcases.TestcaseType.PUBLIC);
	    
        List<FetchTestcaseDto> testcaseDtos = testcases.stream()
                .map(tc -> new FetchTestcaseDto(
                        tc.getTestcaseId(),
                        unwrap(parseJson(tc.getInputValues())),      
                        unwrap(parseJson(tc.getExpectedOutput())),   
                        tc.getDescription(),
                        tc.getWeightage()))
                .collect(Collectors.toList());

       
        return new FetchCodingQuestionDto(
                question.getCodingQuestionId(),
                question.getQuestion(),
                question.getDescription(),
                question.getJavaBoilerCode(),
                question.getPythonBoilerCode(),
                unwrap(parseJson(question.getInputParams())),     
                unwrap(parseJson(question.getInputType())),       
                question.getOutputFormat(),
                testcaseDtos
        );
	}

	// parse Json string into java object
	private Object parseJson(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, Object.class);
        } 
        catch (Exception e) {
            return jsonString; 
        }
    }

	private Object unwrap(Object json) {
        if (json instanceof java.util.Map<?, ?> map) {
            // if single key ,return its value
            if (map.size() == 1) {
                return map.values().iterator().next();
            }
            // if multiple keys ,return all values as list
            return map.values().stream().toList();
        }
        return json;
    }
	
}
