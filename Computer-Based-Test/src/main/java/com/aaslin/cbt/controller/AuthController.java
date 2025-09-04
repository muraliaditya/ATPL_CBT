package com.aaslin.cbt.controller;

import com.aaslin.cbt.entity.User;
import com.aaslin.cbt.service.UserService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins="*")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user, HttpSession session) {
        try {
            User savedUser = userService.login(user);

            // If the user is ADMIN - store session details
            if (savedUser.getRole() == User.Role.ADMIN) {
                session.setAttribute("adminId", savedUser.getUserId());
                session.setAttribute("email", savedUser.getEmail());
                session.setAttribute("collegeUid", savedUser.getCollegeUid());
                session.setAttribute("collegeRollNo", savedUser.getCollegeRollNo());
                session.setAttribute("role", savedUser.getRole().name());
            }

            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            e.printStackTrace(); //  Log the actual error
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}