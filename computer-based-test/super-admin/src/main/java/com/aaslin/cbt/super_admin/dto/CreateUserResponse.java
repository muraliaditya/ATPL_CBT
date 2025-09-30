package com.aaslin.cbt.super_admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResponse {
	private String message;
	private String status;
	private String role;
	private String userId;

}
