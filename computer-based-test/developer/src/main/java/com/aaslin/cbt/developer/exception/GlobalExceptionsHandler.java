package com.aaslin.cbt.developer.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionsHandler {

    @ExceptionHandler(InvalidMcqRequestException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidRequest(InvalidMcqRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "statusCode", 400,
                        "message", ex.getMessage(),
                        "status", "error"
                ));
    }

    @ExceptionHandler(McqDataAccessException.class)
    public ResponseEntity<Map<String, Object>> handleDatabaseException(McqDataAccessException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "statusCode", 500,
                        "message", ex.getMessage(),
                        "status", "error"
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "statusCode", 500,
                        "message", "Server error: " + ex.getMessage(),
                        "status", "error"
                ));
    }
}
