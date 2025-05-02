package com.petplace.service;

import com.petplace.dto.PlacesResponseDto;
import com.petplace.entity.Places;
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

/**
 * 장소 서비스
 * 장소 관련 비즈니스 로직 처리
 */
@Service
@RequiredArgsConstructor
public class PlacesService {
    private final PlacesRepository placesRepository;
    private final VisitedPlacesRepository visitedPlacesRepository;
    private final PetSizeClassificationService petSizeClassificationService;

    /**
     * 필터를 적용하여 장소 목록 조회
     *
     * @param search 검색어
     * @param industry 산업 카테고리
     * @param city 도시
     * @param district 지역구
     * @param petSize 반려견 크기
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @return 조회 결과를 담은 맵
     */
    public Map<String, Object> getPlaces(String search, String industry, String city, String district, String petSize, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("placeId").descending());
        Page<Places> placesPage = placesRepository.findPlacesWithFilters(search, industry, city, district, petSize, pageable);

        // 엔티티를 DTO로 변환
        Page<PlacesResponseDto> placesDto = placesPage.map(place -> {
            PlacesResponseDto dto = PlacesResponseDto.fromEntity(place);
            if (place.getPetSize() != null) {
                dto.setPetSizeCategories(petSizeClassificationService.getPetSizeCategories(place.getPetSize()));
            }
            return dto;
        });

        // 응답 맵 구성
        Map<String, Object> response = new HashMap<>();
        response.put("places", placesDto.getContent());
        response.put("currentPage", placesPage.getNumber() + 1);
        response.put("totalItems", placesPage.getTotalElements());
        response.put("totalPages", placesPage.getTotalPages());
        response.put("totalCount", placesPage.getTotalElements()); // totalItems와 동일

        return response;
    }

    /**
     * ID로 장소 조회
     *
     * @param placeId 장소 ID
     * @return 조회된 장소 DTO
     */
    public PlacesResponseDto getPlaceById(Long placeId) {
        Places place = placesRepository.findById(placeId)
                .orElseThrow(() -> new RuntimeException("Place not found with id: " + placeId));

        PlacesResponseDto placeDto = PlacesResponseDto.fromEntity(place);

        // 반려견 크기 카테고리 설정
        if (place.getPetSize() != null) {
            placeDto.setPetSizeCategories(petSizeClassificationService.getPetSizeCategories(place.getPetSize()));
        }

        return placeDto;
    }

    /**
     * 모든 도시 목록 조회
     *
     * @return 도시 목록
     */
    public List<String> getAllCities() {
        return placesRepository.findDistinctCities();
    }

    /**
     * 모든 산업 카테고리 목록 조회
     *
     * @return 산업 카테고리 목록
     */
    public List<String> getAllIndustries() {
        return placesRepository.findDistinctIndustries();
    }

    /**
     * 모든 장소 조회
     *
     * @return 전체 장소 목록
     */
    public List<Places> getAllPlaces() {
        return placesRepository.findAll();
    }

    /**
     * 키워드로 장소 검색
     *
     * @param keyword 검색 키워드
     * @return 검색된 장소 목록
     */
    public List<Places> searchPlacesByKeyword(String keyword) {
        return placesRepository.findByPlaceNameContainingIgnoreCaseOrRoadAddressContainingIgnoreCase(keyword, keyword);
    }
}