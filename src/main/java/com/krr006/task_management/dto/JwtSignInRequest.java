package com.krr006.task_management.dto;

import lombok.Data;

@Data
public class JwtSignInRequest {
    private String username;
    private String password;
}
