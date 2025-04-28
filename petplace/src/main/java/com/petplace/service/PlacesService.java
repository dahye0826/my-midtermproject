package com.petplace.service;

import com.petplace.dto.PlaceVisitedInfoDto;
import com.petplace.dto.PlacesResponseDto;
import com.petplace.entity.Places;
import com.petplace.entity.VisitedPlaces;
import com.petplace.repository.PlacesRepository;
import com.petplace.repository.VisitedPlacesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlacesService {
    private final PlacesRepository placesRepository;
    private final VisitedPlacesRepository visitedPlacesRepository;
    private final PetSizeClassificationService petSizeClassificationService;

    public Map<String, Object> getPlaces(String search, String industry, String city, String district, String petSize, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("placeId").descending());
        Page<Places> placesPage = placesRepository.findPlacesWithFilters(search, industry, city, district, petSize, pageable);

        Page<PlacesResponseDto> placesDto = placesPage.map(place -> {
            PlacesResponseDto dto = PlacesResponseDto.fromEntity(place);
            if (place.getPetSize() != null) {
                dto.setPetSizeCategories(petSizeClassificationService.getPetSizeCategories(place.getPetSize()));
            }
            return dto;
        });

        Map<String, Object> response = new HashMap<>();
        response.put("places", placesDto.getContent());
        response.put("currentPage", placesPage.getNumber() + 1);
        response.put("totalItems", placesPage.getTotalElements());
        response.put("totalPages", placesPage.getTotalPages());
        response.put("totalCount", placesPage.getTotalElements()); // Duplicate of totalItems

        return response;
    }

    public PlacesResponseDto getPlaceById(Long placeId) {
        Places place = placesRepository.findById(placeId)
                .orElseThrow(() -> new RuntimeException("Place not found with id: " + placeId));

        PlacesResponseDto placeDto = PlacesResponseDto.fromEntity(place);

        // Set pet size categories if available
        if (place.getPetSize() != null) {
            placeDto.setPetSizeCategories(petSizeClassificationService.getPetSizeCategories(place.getPetSize()));
        }

        return placeDto;
    }

    public List<String> getAllCities() {
        return placesRepository.findDistinctCities();
    }

    public List<String> getAllIndustries() {
        return placesRepository.findDistinctIndustries();
    }

    public List<Places> getAllPlaces() {
        return placesRepository.findAll();
    }

    public List<Places> searchPlacesByKeyword(String keyword) {
        return placesRepository.findByPlaceNameContainingIgnoreCaseOrRoadAddressContainingIgnoreCase(keyword, keyword);
    }
    public List<PlacesResponseDto> findPlacesByIds(List<Long> placeIds) {
        return placesRepository.findByPlaceIdIn(placeIds)
                .stream()
                .map(PlacesResponseDto::fromEntity)
                .toList();
    }
}