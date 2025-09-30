package com.aaslin.cbt.common.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles_cbt")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Roles {

    @Id
    @Column(name = "role_id", length = 50)
    private String roleId;

    @Column(name = "role", nullable = false, length = 100)
    private String role;

    @OneToMany(mappedBy = "role")
    private List<User> users;
}