package com.aaslin.cbt.participant.dto;

import lombok.Setter;

@Setter
public class ParticipantResponse {

	private String participantId;
	private String token;
	public ParticipantResponse(String participantId, String accessToken) {
		super();
		this.participantId = participantId;
		this.token = accessToken;
	}
	public String getParticipantId() {
		return participantId;
	}
	
	public String getAccessToken() {
		return token;
	}

	
}
