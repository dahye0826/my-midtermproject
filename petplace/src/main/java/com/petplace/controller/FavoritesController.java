package com.petplace.controller;

import com.petplace.dto.FavoritesResponseDto;
import com.petplace.service.FavoritesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoritesController {

    private final FavoritesService favoritesService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getFavorites(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long placeId,
            @RequestParam(defaultValue = "10") int size) {

        Map<String, Object> response = favoritesService.getFavorites(userId, placeId, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{favoriteId}")
    public ResponseEntity<FavoritesResponseDto> getFavoriteById(@PathVariable Long favoriteId) {
        FavoritesResponseDto favorite = favoritesService.getFavoriteById(favoriteId);
        return ResponseEntity.ok(favorite);
    }

    @PostMapping("/toggle")
    public ResponseEntity<?> toggleFavorite(@RequestParam Long userId, @RequestParam Long placeId) {
        try {
            boolean isAdded = favoritesService.toggleFavorite(userId, placeId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("isAdded", isAdded);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "즐겨찾기 업데이트 중 오류가 발생했습니다.");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}