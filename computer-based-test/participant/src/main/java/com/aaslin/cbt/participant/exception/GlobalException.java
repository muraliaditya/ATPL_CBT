package com.aaslin.cbt.participant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("statusCode", status.value());
        body.put("errorMessage", message);
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(CustomException.ContestNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleContestNotFound(CustomException.ContestNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(CustomException.MCQNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleMcqNotFound(CustomException.MCQNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(CustomException.SubmissionNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleSubmissionNotFound(CustomException.SubmissionNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(CustomException.ParticipantValidationException.class)
    public ResponseEntity<Map<String, Object>> handleParticipantValidation(CustomException.ParticipantValidationException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(CustomException.UnsupportedCategoryException.class)
    public ResponseEntity<Map<String, Object>> handleUnsupportedCategory(CustomException.UnsupportedCategoryException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(CustomException.InternalServerException.class)
    public ResponseEntity<Map<String, Object>> handleInternalServer(CustomException.InternalServerException ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(CustomException ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleUnexpected(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong: " + ex.getMessage());
    }
}