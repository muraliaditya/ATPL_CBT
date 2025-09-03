package com.aaslin.computer_based_test.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aaslin.computer_based_test.model.User;
import com.aaslin.computer_based_test.model.User.Role;
import com.aaslin.computer_based_test.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;

	// Common login for USER and ADMIN
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String email,
	                               @RequestParam String collegeId,
	                               @RequestParam String collegeRollNo,
	                               @RequestParam Role role) {

	        if (role == Role.ADMIN) {
	            User admin = userService.adminLogin();
	            return ResponseEntity.ok(admin);
	        } else {
	            Optional<User> user = userService.login(email, collegeId, collegeRollNo);
	            if (user.isPresent() && user.get().getRole() == Role.USER) {
	                return ResponseEntity.ok(user.get());
	            } 
	            else {
	                return ResponseEntity.status(401).body("Invalid login details for USER");
	            }
	        }
	 }
}


