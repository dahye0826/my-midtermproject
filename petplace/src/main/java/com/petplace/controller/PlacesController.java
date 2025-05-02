package com.petplace.controller;

import com.petplace.dto.PlacesResponseDto;
import com.petplace.entity.Places;
import com.petplace.service.PlacesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 장소 컨트롤러
 * 장소 관련 API 엔드포인트 정의
 */
@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlacesController {
    private final PlacesService placesService;

    /**
     * 장소 목록 조회 API
     * 검색어, 카테고리, 지역, 반려견 크기 등으로 필터링 가능
     *
     * @param page 페이지 번호 (기본값: 1)
     * @param search 검색어 (선택)
     * @param industry 산업 카테고리 (선택)
     * @param city 도시 (선택)
     * @param district 지역구 (선택)
     * @param petSize 반려견 크기 (선택)
     * @param size 페이지 크기 (기본값: 9)
     * @return 조회 결과와 페이징 정보
     */
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

    /**
     * ID로 장소 조회 API
     *
     * @param placeId 장소 ID
     * @return 장소 상세 정보
     */
    @GetMapping("/{placeId}")
    public ResponseEntity<PlacesResponseDto> getPlaceById(@PathVariable Long placeId) {
        return ResponseEntity.ok(placesService.getPlaceById(placeId));
    }

    /**
     * 모든 도시 목록 조회 API
     *
     * @return 도시 목록
     */
    @GetMapping("/cities")
    public ResponseEntity<List<String>> getAllCities() {
        return ResponseEntity.ok(placesService.getAllCities());
    }

    /**
     * 모든 카테고리 목록 조회 API
     *
     * @return 카테고리 목록
     */
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        return ResponseEntity.ok(placesService.getAllIndustries());
    }

    /**
     * 모든 장소 조회 API
     *
     * @return 전체 장소 목록
     */
    @GetMapping("/all")
    public List<Places> getAllPlaces(){
        return placesService.getAllPlaces();
    }

    /**
     * 키워드로 장소 검색 API
     *
     * @param keyword 검색 키워드
     * @return 검색된 장소 목록
     */
    @GetMapping("/search")
    public List<Places> searchPlaces(@RequestParam String keyword) {
        return placesService.searchPlacesByKeyword(keyword);
    }
}