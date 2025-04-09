package com.petplace.service;

import com.petplace.dto.VisitedPlacesResponseDto;
import com.petplace.entity.VisitedPlaces;
import com.petplace.repository.VisitedPlacesRepository;
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
public class VisitedPlacesService {

    private final VisitedPlacesRepository visitedPlacesRepository;

    public Map<String, Object> getVisitedPlaces(Long userId, Long placeId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("visitId").descending());

        Page<VisitedPlaces> visitedPlacesPage = visitedPlacesRepository.findVisitedPlacesWithFilters(userId, placeId, pageable);

        Page<VisitedPlacesResponseDto> visitedPlacesDto = visitedPlacesPage.map(VisitedPlacesResponseDto::fromEntity);

        Map<String, Object> response = new HashMap<>();
        response.put("visitedPlaces", visitedPlacesDto.getContent());
        response.put("currentPage", visitedPlacesPage.getNumber() + 1);
        response.put("totalItems", visitedPlacesPage.getTotalElements());
        response.put("totalPages", visitedPlacesPage.getTotalPages());

        return response;
    }

    public VisitedPlacesResponseDto getVisitedPlaceById(Long visitId) {
        VisitedPlaces visitedPlace = visitedPlacesRepository.findById(visitId)
                .orElseThrow(() -> new RuntimeException("Visited place not found with id: " + visitId));
        return VisitedPlacesResponseDto.fromEntity(visitedPlace);
    }
}