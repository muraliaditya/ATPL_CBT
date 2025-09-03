package com.aaslin.computer_based_test.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.aaslin.computer_based_test.model.User;
import com.aaslin.computer_based_test.model.User.Role;
import com.aaslin.computer_based_test.repository.UserRepository;

public class UserService {
	 @Autowired
	    private UserRepository userRepository;

	    // Login without password
	    public Optional<User> login(String email, String collegeId, String collegeRollNo) {
	        return userRepository.findByEmailAndCollegeIdAndCollegeRollNo(email, collegeId, collegeRollNo);
	    }

	    // Special Admin login (dummy values)
	    public User adminLogin() {
	        User admin = new User();
	        admin.setEmail("admin@system.com");
	        admin.setCollegeId("NA");
	        admin.setCollegeRollNo("NA");
	        admin.setRole(Role.ADMIN);
	        return admin;
	    }
}
