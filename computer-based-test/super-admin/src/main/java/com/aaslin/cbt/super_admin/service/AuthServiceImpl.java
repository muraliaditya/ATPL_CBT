package com.aaslin.cbt.super_admin.service;

import java.time.LocalDateTime;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aaslin.cbt.common.model.Roles;
import com.aaslin.cbt.common.model.User;
import com.aaslin.cbt.super_admin.dto.CreateUserRequest;
import com.aaslin.cbt.super_admin.dto.CreateUserResponse;
import com.aaslin.cbt.super_admin.dto.LoginRequest;
import com.aaslin.cbt.super_admin.dto.LoginResponse;
import com.aaslin.cbt.super_admin.repository.RoleRepository;
import com.aaslin.cbt.super_admin.repository.UsersRepository;
import com.aaslin.cbt.super_admin.util.CustomIdGenerator;
import com.aaslin.cbt.super_admin.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsersRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        LoginResponse response = new LoginResponse();
        response.setAccessToken(jwtUtil.generateToken(user.getUsername()));
        response.setRefreshToken(jwtUtil.generateRefreshToken(user.getUsername()));
        response.setUserId(user.getUserId());
        response.setRole(user.getRole().getRole());
        response.setExpiresIn(3600);
        response.setMessage("Login successful. Welcome, " + user.getUsername() + "!");
        response.setStatus("SUCCESS");
        return response;
    }

    @Override
    public CreateUserResponse createSuperAdmin(CreateUserRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return new CreateUserResponse("SuperAdmin with username '" + request.getUsername() + "' already exists",
                                          "ERROR", "SUPER_ADMIN", null);
        }

        Roles role = roleRepository.findByRole("SUPER_ADMIN")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        String lastId = userRepository.findLastSuperAdminId();
        String newId = CustomIdGenerator.generateNextId("SADM", lastId);

        User superAdmin = new User();
        superAdmin.setUserId(newId);
        superAdmin.setUsername(request.getUsername());
        superAdmin.setPassword(passwordEncoder.encode(request.getPassword()));
        superAdmin.setRole(role);
        superAdmin.setCreatedAt(LocalDateTime.now());
        superAdmin.setUpdatedAt(LocalDateTime.now());

        userRepository.save(superAdmin);

        return new CreateUserResponse("SuperAdmin created successfully", "SUCCESS", role.getRole(), newId);
    }

    @Override
    public CreateUserResponse createDeveloper(CreateUserRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return new CreateUserResponse(
                    "Developer with username '" + request.getUsername() + "' already exists",
                    "ERROR", "DEVELOPER", null
            );
        }

        Roles role = roleRepository.findByRole("DEVELOPER")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User superAdmin = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Super Admin not found"));

        if (!"SUPER_ADMIN".equals(superAdmin.getRole().getRole())) {
            throw new RuntimeException("Only SuperAdmins can create Developers!");
        }

        String lastId = userRepository.findLastDeveloperId();
        String newId = CustomIdGenerator.generateNextId("DEV", lastId);

        User dev = new User();
        dev.setUserId(newId);
        dev.setUsername(request.getUsername());
        dev.setPassword(passwordEncoder.encode(request.getPassword()));
        dev.setRole(role);
        dev.setCreatedAt(LocalDateTime.now());
        dev.setCreatedBy(superAdmin);
        dev.setUpdatedAt(LocalDateTime.now());
        dev.setUpdatedBy(superAdmin);

        userRepository.save(dev);

        return new CreateUserResponse("Developer created successfully", "SUCCESS", role.getRole(), newId);
    }


    @Override
    public LoginResponse refreshAccessToken(String refreshToken) {
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String username = jwtUtil.extractUsername(refreshToken);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LoginResponse response = new LoginResponse();
        response.setAccessToken(jwtUtil.generateToken(user.getUsername()));
        response.setRefreshToken(refreshToken);
        response.setUserId(user.getUserId());
        response.setRole(user.getRole().getRole());
        response.setExpiresIn(3600);
        response.setMessage("Token refreshed successfully");
        response.setStatus("SUCCESS");
        return response;
    }
}