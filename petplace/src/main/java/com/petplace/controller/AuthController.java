package com.petplace.controller;

import com.petplace.dto.LoginRequestDto;
import com.petplace.dto.SignupRequestDto;
import com.petplace.dto.UserResponseDto;
import com.petplace.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequestDto loginRequestDto) {
        Map<String, Object> response = authService.login(loginRequestDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody SignupRequestDto signupRequestDto) {
        UserResponseDto userResponseDto = authService.signup(signupRequestDto);
        return ResponseEntity.ok(userResponseDto);
    }
}