package com.aaslin.cbt.super_admin.dto;

import lombok.Data;
import java.util.List;

@Data
public class AssignCodingQuestionsRequest {
    private String contestId;
    private List<String> codingQuestionIds;
}
