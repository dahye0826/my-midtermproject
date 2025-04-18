package com.petplace.controller;

import com.petplace.dto.PlacesResponseDto;
import com.petplace.entity.Places;
import com.petplace.service.PlacesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlacesController {
    @Autowired
    private final PlacesService placesService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getPlaces(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String industry,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String district,
            @RequestParam(defaultValue = "9") int size) {  // Changed default size to 9 to match frontend

        Map<String, Object> response = placesService.getPlaces(search, industry, city, district, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{placeId}")
    public ResponseEntity<PlacesResponseDto> getPlaceById(@PathVariable Long placeId) {
        PlacesResponseDto place = placesService.getPlaceById(placeId);
        return ResponseEntity.ok(place);
    }

    // Add an endpoint to get cities for the dropdown
    @GetMapping("/cities")
    public ResponseEntity<List<String>> getAllCities() {
        List<String> cities = placesService.getAllCities();
        return ResponseEntity.ok(cities);
    }

    // Add an endpoint to get industries (categories) for the dropdown
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        List<String> categories = placesService.getAllIndustries();
        return ResponseEntity.ok(categories);
    }

    // @RequestBody
    // @PathVariable
    @GetMapping("/nearby")
    public ResponseEntity<List<Places>> findNearbyPlaces(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam(defaultValue = "100") double radius // 반경 m 단위
    ) {
        List<Places> nearbyPlaces = placesService.findNearbyPlaces(lat, lng, radius);
        return ResponseEntity.ok(nearbyPlaces);
    }

}