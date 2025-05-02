package com.petplace.controller;

import com.petplace.dto.RecommendationRequestDto;

import com.petplace.service.PlaceViewService;
import com.petplace.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RecommendationController {

    private final PlaceViewService placeViewService;
    private final RecommendationService recommendationService;

    @GetMapping("/recommend")
    public ResponseEntity<Map<String, List<Integer>>> recommend(@RequestParam Long userId) {
        List<RecommendationRequestDto.RecentViewDto> recentViews = placeViewService.getRecentViewDtos(userId);
        List<Integer> recommendedPlaceIds = recommendationService.getRecommendedPlaces(userId, recentViews);

        return ResponseEntity.ok(Map.of("recommendedPlaceIds", recommendedPlaceIds));
    }
}