package com.petplace.controller;

import com.petplace.dto.PlacesResponseDto;
import com.petplace.service.PlacesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlacesController {

    private final PlacesService placesService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getPlaces(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String industry,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String district,
            @RequestParam(defaultValue = "10") int size) {

        Map<String, Object> response = placesService.getPlaces(search, industry, city, district, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{placeId}")
    public ResponseEntity<PlacesResponseDto> getPlaceById(@PathVariable Long placeId) {
        PlacesResponseDto place = placesService.getPlaceById(placeId);
        return ResponseEntity.ok(place);
    }
}