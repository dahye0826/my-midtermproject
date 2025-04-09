package com.petplace.controller;

import com.petplace.dto.VisitedPlacesResponseDto;
import com.petplace.service.VisitedPlacesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/visited-places")
@RequiredArgsConstructor
public class VisitedPlacesController {

    private final VisitedPlacesService visitedPlacesService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getVisitedPlaces(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long placeId,
            @RequestParam(defaultValue = "10") int size) {

        Map<String, Object> response = visitedPlacesService.getVisitedPlaces(userId, placeId, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{visitId}")
    public ResponseEntity<VisitedPlacesResponseDto> getVisitedPlaceById(@PathVariable Long visitId) {
        VisitedPlacesResponseDto visitedPlace = visitedPlacesService.getVisitedPlaceById(visitId);
        return ResponseEntity.ok(visitedPlace);
    }
}