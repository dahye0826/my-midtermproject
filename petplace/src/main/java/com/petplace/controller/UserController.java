package com.petplace.controller;

import com.petplace.dto.UserResponseDto;
import com.petplace.dto.UserUpdateRequestDto;
import com.petplace.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        return ResponseEntity.ok(userService.getUser(search, role, page, size));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long userId,
            @RequestBody UserUpdateRequestDto updateRequestDto) {
        return ResponseEntity.ok(userService.updateUser(userId, updateRequestDto));
    }

    @GetMapping("/{userId}/posts")
    public ResponseEntity<Map<String, Object>> getUserPosts(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(userService.getUserPosts(userId, page, size));
    }

    @GetMapping("/{userId}/visited-places")
    public ResponseEntity<Map<String, Object>> getUserVisitedPlaces(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(userService.getUserVisitedPlaces(userId, page, size));
    }
}