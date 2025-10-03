package com.aaslin.cbt.participant.exception;

public class CustomException extends RuntimeException {

	public CustomException(String message) {
		super(message);
	}
	public CustomException(String message, Throwable cause) {
		
	}
	public static class InternalServerException extends CustomException {
		public InternalServerException(String message,Throwable cause) {
			super(message,cause);
		}

	}

	public static class SubmissionNotFoundException extends CustomException {

		public SubmissionNotFoundException(String message) {
			super(message);
		}

	}

	public static class MCQNotFoundException extends CustomException {

		public MCQNotFoundException(String message) {
			super(message);
		}
	}

	public static class ContestNotFoundException extends CustomException{

		private static final long serialVersionUID = 1L;

		public ContestNotFoundException(String message) {
			super(message);
		}
	}
	
	public static class ParticipantValidationException extends CustomException{

		private static final long serialVersionUID = 1L;

		public ParticipantValidationException(String message) {
			super(message);
		}
	}
	public static class UnsupportedCategoryException extends CustomException{


		private static final long serialVersionUID = 1L;

		public UnsupportedCategoryException(String message) {
			super(message);
		}
	}
	
}
