package com.petplace.controller;


import com.petplace.dto.RecommendationRequestDto;

import com.petplace.dto.PlaceViewRequestDto;
import com.petplace.service.PlaceViewService;

import com.petplace.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/track")
public class PlaceViewController {

    private final PlaceViewService placeViewService;
    private final RecommendationService recommendationService;

    @PostMapping("/place-view")
    public ResponseEntity<?> trackPlaceView(@RequestBody PlaceViewRequestDto request) {
        System.out.println("Received place view request: " + request);
        placeViewService.savePlaceView(request);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/recommend")
    public ResponseEntity<Map<String, List<Integer>>> recommend(@RequestParam Long userId) {
        List<RecommendationRequestDto.RecentViewDto> recentViews = placeViewService.getRecentViewDtos(userId);

        List<Integer> recommendedPlaceIds = recommendationService.getRecommendedPlaces(userId, recentViews);
        return ResponseEntity.ok(Map.of("recommendedPlaceIds", recommendedPlaceIds));
    }


}
