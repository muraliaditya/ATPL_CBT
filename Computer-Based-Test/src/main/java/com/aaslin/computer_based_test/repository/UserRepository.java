package com.aaslin.computer_based_test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aaslin.computer_based_test.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndCollegeIdAndCollegeRollNo(String email, String collegeId, String collegeRollNo);
}

