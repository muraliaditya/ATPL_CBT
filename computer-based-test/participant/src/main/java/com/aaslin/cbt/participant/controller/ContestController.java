package com.aaslin.cbt.participant.controller;

import com.aaslin.cbt.participant.dto.*;
import com.aaslin.cbt.participant.exception.CustomException;
import com.aaslin.cbt.participant.exception.ErrorResponse;
import com.aaslin.cbt.participant.service.ContestService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**@author ATPLD14
 * 
 */
@RestController("participant")
@RequestMapping("/api/v1/participant/contests")
public class ContestController {

    private final ContestService service;

    public ContestController(ContestService service) {
        this.service = service;
    }

    @GetMapping("/{contestId}/eligibility")
    public ResponseEntity<?> getEligibility(@PathVariable String contestId) {
        try {
            ContestEligibilityResponse response = service.checkEligibility(contestId);
            return ResponseEntity.ok(response);
        } catch (CustomException.ContestNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, e.getMessage()));
        }
    }

    @GetMapping("/{contestId}/basic-info")
    public ResponseEntity<?> contestDetails(@PathVariable String contestId) {
        try {
            ContestDetailsResponse response = service.getContestInfo(contestId);
            return ResponseEntity.ok(response);
        } catch (CustomException.ContestNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, e.getMessage()));
        }
    }

    @GetMapping("/{contestId}/start")
    public ResponseEntity<?> startContest(@PathVariable String contestId) {
        try {
            ContestStartResponse response = service.startContest(contestId);
            return ResponseEntity.ok(response);
        } catch (CustomException.ContestNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, e.getMessage()));
        }
    }

    @PostMapping("/{contestId}/submit")
    public ResponseEntity<?> submitTest(@PathVariable String contestId,@RequestBody TestSubmissionRequest request) {
        try {
            request.setContestId(contestId);
            TestSubmissionResponse response = service.saveTestSubmission(request);
            return ResponseEntity.ok(response);
        } catch (CustomException.SubmissionNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, e.getMessage()));
        } catch (CustomException.MCQNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, e.getMessage()));
        } catch (CustomException.InternalServerException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, e.getMessage()));
        }
    }

    
}