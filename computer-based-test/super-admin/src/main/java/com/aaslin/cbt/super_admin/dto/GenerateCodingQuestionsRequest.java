package com.aaslin.cbt.super_admin.dto;

import lombok.Data;
import java.util.List;

@Data
public class GenerateCodingQuestionsRequest {
    private Integer count; 
    private List<Preference> preferences;

    @Data
    public static class Preference {
        private Integer questionNo;
        private String preference; 
    }
}
