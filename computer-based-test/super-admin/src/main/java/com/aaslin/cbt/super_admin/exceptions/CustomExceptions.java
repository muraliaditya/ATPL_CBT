package com.aaslin.cbt.super_admin.exceptions;

public class CustomExceptions{
	public static class SectionNotFoundException extends RuntimeException {
	    public SectionNotFoundException(String sectionName) {
	        super("Section not found: " + sectionName);
	    }
	}

	public static class McqNotFoundException extends RuntimeException {
	    public McqNotFoundException(String mcqId) {
	        super("MCQ not found: " + mcqId);
	    }
	}

	public static class ContestNotFoundException extends RuntimeException{
	public ContestNotFoundException(String contestId) {
		super("Contest not Found:" + contestId);
		}
	}
	public static class CategoryIdNotFoundException extends RuntimeException{
		public CategoryIdNotFoundException(String categoryName) {
			super("Category not found" + categoryName);
		}
	}
	public static class SubmissionIdNotFoundException extends RuntimeException{
		public SubmissionIdNotFoundException(String submissionId) {
			super("SubmissionId Not Found:" + submissionId);
		}
	}
	
}

