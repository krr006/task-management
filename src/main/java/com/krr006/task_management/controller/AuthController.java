package com.krr006.task_management.controller;

import com.krr006.task_management.dto.JwtResponse;
import com.krr006.task_management.dto.JwtSignInRequest;
import com.krr006.task_management.dto.JwtSignUpRequest;
import com.krr006.task_management.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public JwtResponse signUp(@RequestBody @Valid JwtSignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/sign-in")
    public JwtResponse signIn(@RequestBody @Valid JwtSignInRequest request) {
        return authenticationService.signIn(request);
    }
}
