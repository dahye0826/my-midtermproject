package com.petplace.controller;
import com.petplace.dto.VisitedPlacesRequestDto;
import com.petplace.dto.VisitedPlacesResponseDto;
import com.petplace.entity.VisitedPlaces;
import com.petplace.repository.PlacesRepository;
import com.petplace.repository.UserRepository;
import com.petplace.repository.VisitedPlacesRepository;
import com.petplace.service.VisitedPlacesService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/visited-place")
@RequiredArgsConstructor
public class VisitedPlacesController {
    private final VisitedPlacesService visitedPlacesService;
    private static final Logger logger = LoggerFactory.getLogger(VisitedPlacesController.class);

    //  특정 장소의 리뷰 전체 조회 (장소 상세 페이지)
    @GetMapping("/reviews")
    public ResponseEntity<List<VisitedPlacesResponseDto>> getByPlaceId(@RequestParam Long placeId) {
        return ResponseEntity.ok(visitedPlacesService.getVisitedPlacesByPlaceId(placeId));
    }

    // 마이페이지에서 방문 기록 전체 조회
    @GetMapping("/mypage")
    public ResponseEntity<?> getVisitedPlaces(
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Map<String, Object> response = visitedPlacesService.getVisitedPlacesByUserId(userId, page, size);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("방문 이력 조회 실패", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "방문 이력을 불러오는데 실패했습니다.");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // 방문 이력 개수 조회
    @GetMapping("/count")
    public ResponseEntity<?> getVisitedPlacesCount(@RequestParam Long userId) {
        try {
            long count = visitedPlacesService.countVisitedPlacesByUserId(userId);
            Map<String, Object> response = new HashMap<>();
            response.put("count", count);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("방문 이력 개수 조회 실패", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "방문 이력 개수를 불러오는데 실패했습니다.");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // 방문 이력 등록 (마이페이지 또는 리뷰 공통)
    @PostMapping
    public ResponseEntity<?> addVisitedPlace(@RequestBody VisitedPlacesRequestDto requestDto) {
        try {
            VisitedPlacesResponseDto savedVisit = visitedPlacesService.addVisitedPlace(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedVisit);
        } catch (Exception e) {
            logger.error("방문 이력 추가 실패", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "방문 이력을 추가하는데 실패했습니다.");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // 방문 이력 수정
    @PutMapping("/{visitId}")
    public ResponseEntity<?> updateVisitedPlace(
            @PathVariable Long visitId,
            @RequestBody VisitedPlacesRequestDto requestDto) {
        try {
            VisitedPlacesResponseDto updatedVisit = visitedPlacesService.updateVisitedPlace(visitId, requestDto);
            return ResponseEntity.ok(updatedVisit);
        } catch (Exception e) {
            logger.error("방문 이력 수정 실패", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "방문 이력을 수정하는데 실패했습니다.");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // 방문 이력 삭제
    @DeleteMapping("/{visitId}")
    public ResponseEntity<?> deleteVisitedPlace(@PathVariable Long visitId) {
        try {
            visitedPlacesService.deleteVisitedPlace(visitId);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "방문 이력이 성공적으로 삭제되었습니다.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("방문 이력 삭제 실패", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "방문 이력을 삭제하는데 실패했습니다.");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}




