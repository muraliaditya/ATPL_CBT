package com.aaslin.cbt.participant.mapper;

import com.aaslin.cbt.common.model.Participant;
import com.aaslin.cbt.participant.dto.ParticipantRequest;

public class ParticipantMapper {

    public static Participant toParticipantEntity(ParticipantRequest request) {
        Participant participant = new Participant();
        participant.setName(request.getName());
        participant.setEmail(request.getEmail());
        participant.setCollegeRegdNo(request.getCollegeRegdNo());
        participant.setHighestDegree(request.getHighestDegree());
        participant.setOverallExperience(request.getOverallExperience());
        return participant;
    }
}