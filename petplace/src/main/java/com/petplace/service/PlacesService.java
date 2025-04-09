package com.petplace.service;

import com.petplace.dto.PlacesResponseDto;
import com.petplace.entity.Places;
import com.petplace.repository.PlacesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PlacesService {

    private final PlacesRepository placesRepository;

    public Map<String, Object> getPlaces(String search, String industry, String city, String district, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("placeId").descending());

        Page<Places> placesPage = placesRepository.findPlacesWithFilters(search, industry, city, district, pageable);

        Page<PlacesResponseDto> placesDto = placesPage.map(PlacesResponseDto::fromEntity);

        Map<String, Object> response = new HashMap<>();
        response.put("places", placesDto.getContent());
        response.put("currentPage", placesPage.getNumber() + 1);
        response.put("totalItems", placesPage.getTotalElements());
        response.put("totalPages", placesPage.getTotalPages());

        return response;
    }

    public PlacesResponseDto getPlaceById(Long placeId) {
        Places place = placesRepository.findById(placeId)
                .orElseThrow(() -> new RuntimeException("Place not found with id: " + placeId));
        return PlacesResponseDto.fromEntity(place);
    }
}