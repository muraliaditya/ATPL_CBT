package com.aaslin.cbt.developer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aaslin.cbt.common.model.User;

public interface UserRepository extends JpaRepository<User,String>{
	Optional<User> findByUsername(String username);

}
