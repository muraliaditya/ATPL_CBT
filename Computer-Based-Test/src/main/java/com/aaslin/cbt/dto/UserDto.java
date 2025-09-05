package com.aaslin.cbt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String userId;
    private String role;
    private String email;
    private String collegeUid;
    private String collegeRollNo;
}

