package com.cbt.mcq_test.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @Column(name = "user_id", length = 36)
    private String userId;  

    @Column(nullable = false, unique = true, length = 255)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;  

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    public enum Role {
        SUPERADMIN, DEVELOPER
    }
}

