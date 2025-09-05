package com.aaslin.cbt.controller;

import com.aaslin.cbt.dto.LoginResponseDto;
import com.aaslin.cbt.dto.UserDto;
import com.aaslin.cbt.entity.Contest;
import com.aaslin.cbt.entity.User;
import com.aaslin.cbt.repository.ContestRepository;
import com.aaslin.cbt.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;
    
    @Autowired 
    private ContestRepository contestRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody User userInput) {
        User user = authService.authenticateUserAndJoinContest(userInput);

        // Convert User → UserDto
        UserDto userDto = new UserDto(
                user.getUserId(),
                user.getRole().name(),
                user.getEmail(),
                user.getCollegeUid(),
                user.getCollegeRollNo()
        );

        if (user.getRole() == User.Role.ADMIN) {
            // Admin - contestId = null
            return ResponseEntity.ok(new LoginResponseDto(null, userDto));
        }

        // For USER - find contest automatically
        Contest contest = contestRepository.findByStatusAndAllowedCollegeUidsContaining(
                Contest.Status.ACTIVE,
                userInput.getCollegeUid()
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No active contest found"));

        return ResponseEntity.ok(new LoginResponseDto(contest.getContestId(), userDto));
    }

}
