package com.aaslin.cbt.super_admin.service;

import com.aaslin.cbt.super_admin.dto.CreateUserRequest;
import com.aaslin.cbt.super_admin.dto.CreateUserResponse;
import com.aaslin.cbt.super_admin.dto.LoginRequest;
import com.aaslin.cbt.super_admin.dto.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    CreateUserResponse createSuperAdmin(CreateUserRequest request);

    CreateUserResponse createDeveloper(CreateUserRequest request);
}