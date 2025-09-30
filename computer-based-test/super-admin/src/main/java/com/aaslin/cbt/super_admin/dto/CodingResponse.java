package com.aaslin.cbt.super_admin.dto;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class CodingResponse {
    private List<CodingSubmissionDTO> codingSubmissions;
}
