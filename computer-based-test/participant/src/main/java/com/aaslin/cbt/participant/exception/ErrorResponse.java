/**
 * 
 */
package com.aaslin.cbt.participant.exception;

/**@author ATPLD14
 * 
 */
public  class ErrorResponse {
    private int statusCode;
    private String errorMessage;

    public ErrorResponse(int statusCode, String errorMessage) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
