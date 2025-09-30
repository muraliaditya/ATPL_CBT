package com.aaslin.cbt.super_admin.dto;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultResponseDTO {
    private ParticipantInfoDTO participantInfo;
    private McqResponse mcqs;
    private CodingResponse coding;
}
