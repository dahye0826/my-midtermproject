package com.petplace.service;

import com.petplace.dto.PlacesResponseDto;
import com.petplace.dto.VisitedPlacesResponseDto;
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

        PlacesResponseDto placeDto = PlacesResponseDto.fromEntity(place);

        // Get visitedPlaces for this place and add them to the response
        List<VisitedPlaces> visitedPlaces = visitedPlacesRepository.findByPlace_PlaceId(placeId);
        placeDto.setVisitedPlaces(visitedPlaces.stream()
                .map(VisitedPlacesResponseDto::fromEntity)
                .collect(Collectors.toList()));

        return placeDto;
    }

    public List<String> getAllCities() {
        return placesRepository.findDistinctCities();
    }

    public List<String> getAllIndustries() {
        return placesRepository.findDistinctIndustries();
    }

    public List<PlacesResponseDto> searchPlaces(String keyword){
        List<Places> places = placesRepository.findByPlaceNameContainingIgnoreCaseOrRoadAddressContainingIgnoreCase(keyword, keyword);

        return places.stream()
                .map(PlacesResponseDto::fromEntity)
                .collect(Collectors.toList());
    }


    public List<Places> getAllPlaces() {
        return placesRepository.findAll();
    }
}