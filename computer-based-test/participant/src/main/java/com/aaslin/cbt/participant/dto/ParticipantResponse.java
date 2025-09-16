package com.aaslin.cbt.participant.dto;

public class ParticipantResponse {

	private String participantId;
	private String accessToken;
	public ParticipantResponse(String participantId, String accessToken) {
		super();
		this.participantId = participantId;
		this.accessToken = accessToken;
	}
	public String getParticipantId() {
		return participantId;
	}
	
	public String getAccessToken() {
		return accessToken;
	}

	
}
