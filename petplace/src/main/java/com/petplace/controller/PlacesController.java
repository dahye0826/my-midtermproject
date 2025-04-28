package com.petplace.controller;

import com.petplace.dto.PlacesResponseDto;
import com.petplace.entity.Places;
import com.petplace.service.PlacesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
            @RequestParam(required = false) String petSize,
            @RequestParam(defaultValue = "9") int size) {
        return ResponseEntity.ok(placesService.getPlaces(search, industry, city, district, petSize, page, size));
    }

    @GetMapping("/{placeId}")
    public ResponseEntity<PlacesResponseDto> getPlaceById(@PathVariable Long placeId) {
        return ResponseEntity.ok(placesService.getPlaceById(placeId));
    }

    @GetMapping("/cities")
    public ResponseEntity<List<String>> getAllCities() {
        return ResponseEntity.ok(placesService.getAllCities());
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        return ResponseEntity.ok(placesService.getAllIndustries());
    }
    //유다혜
    @GetMapping("/all")
    public List<Places> getAllPlaces(){
       return placesService.getAllPlaces();
    }

    @GetMapping("/search")
    public List<Places> searchPlaces(@RequestParam String keyword) {
        return placesService.searchPlacesByKeyword(keyword);
    }

    @GetMapping("/by-ids")
    public ResponseEntity<List<PlacesResponseDto>> getPlacesByIds(@RequestParam List<Long> placeIds) {
        List<PlacesResponseDto> places = placesService.findPlacesByIds(placeIds);
        return ResponseEntity.ok(places);
    }

}