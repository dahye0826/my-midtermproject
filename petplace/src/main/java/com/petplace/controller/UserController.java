package com.petplace.controller;

import com.petplace.dto.UserResponseDto;
import com.petplace.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String role,
            @RequestParam(defaultValue = "10") int size) {

        Map<String, Object> response = userService.getUsers(search, role, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long userId) {
        UserResponseDto user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }
}