package com.krr006.task_management.dto;

import lombok.Data;

@Data
public class JwtSignUpRequest {
    private String email;
    private String username;
    private String password;
}
