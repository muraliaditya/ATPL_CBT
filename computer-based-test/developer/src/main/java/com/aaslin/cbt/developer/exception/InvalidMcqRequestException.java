package com.aaslin.cbt.developer.exception;

//When request data is invalid
public class InvalidMcqRequestException extends RuntimeException {
	
	public InvalidMcqRequestException(String message) {
		super(message);
	}

	public InvalidMcqRequestException(String message, Throwable cause) {
		super(message, cause);
	}
}

