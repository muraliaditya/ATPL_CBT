package com.aaslin.cbt.super_admin.dto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContestResultsResponse {
    private String eligibility;   
    private List<ContestResultDTO> results;
}
