package com.aaslin.cbt.developer.exception;

public class McqDataAccessException extends RuntimeException {
	
	public McqDataAccessException(String message) {
		   super(message);
		}

		public McqDataAccessException(String message, Throwable cause) {
		   super(message, cause);
		}

}
//When database operation fails
