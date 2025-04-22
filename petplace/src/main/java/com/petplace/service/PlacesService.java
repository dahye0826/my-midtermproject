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
        response.put("totalCount", placesPage.getTotalElements());

        return response;
    }

    public PlacesResponseDto getPlaceById(Long placeId) {
        Places place = placesRepository.findById(placeId)
                .orElseThrow(() -> new RuntimeException("Place not found with id: " + placeId));


        PlacesResponseDto placeDto = PlacesResponseDto.fromEntity(place);
        List<VisitedPlaces> visitedPlaces = visitedPlacesRepository.findAllByPlace_PlaceId(placeId);


        List<PlaceVisitedInfoDto> result = visitedPlaces.stream()
                .map(PlaceVisitedInfoDto::fromEntity)
                .collect(Collectors.toList());
//박병규 오류나서 visitedPlacesdto-> PlaceVisitedInfoDto 로 바꿈(PlaceVisitedInfoDto 파일 생성)
//        문제 없으면 지워주세요
//        List<VisitedPlaces> visitedPlaces = visitedPlacesRepository.findAllByPlace_PlaceId(placeId);
//        placeDto.setVisitedPlaces(visitedPlaces.stream()
//                .map(visitedPlacesDto::fromEntity)
//                .collect(Collectors.toList()));

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

    //유다혜
    public List<Places> getAllPlaces() {

        List<Places> list = placesRepository.findAll();
        return placesRepository.findAll();
    }

    //유다혜
    public List<Places> searchPlacesByKeyword(String keyword) {
        return placesRepository.findByPlaceNameContainingIgnoreCaseOrRoadAddressContainingIgnoreCase(keyword, keyword);
    }
}