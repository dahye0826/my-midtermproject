package com.petplace.controller;

import com.petplace.dto.FavoritesResponseDto;
import com.petplace.service.FavoritesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}