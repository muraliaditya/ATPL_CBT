package com.aaslin.cbt.super_admin.dto;

import lombok.Data;

@Data
public class LoginResponse {
	private String message;
	private String status;
	private String accessToken;
	private String role;
	private String userId;
	private long expiresIn;

}
