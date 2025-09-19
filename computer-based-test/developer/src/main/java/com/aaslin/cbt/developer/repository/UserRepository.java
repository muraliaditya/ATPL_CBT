package com.aaslin.cbt.developer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aaslin.cbt.common.model.User;

public interface UserRepository extends JpaRepository<User,String>{

}
