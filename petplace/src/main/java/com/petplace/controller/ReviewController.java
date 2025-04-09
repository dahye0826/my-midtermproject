package com.petplace.controller;

import com.petplace.dto.ReviewResponseDto;
import com.petplace.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // 리뷰 작성
    @PostMapping
    public ResponseEntity<ReviewResponseDto> createReview(@RequestBody ReviewResponseDto reviewResponseDto) {
        ReviewResponseDto createdReview = reviewService.createReview(reviewResponseDto);
        return ResponseEntity.ok(createdReview);
    }

    // 리뷰 수정
    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponseDto> updateReview(@PathVariable Long id, @RequestBody ReviewResponseDto reviewResponseDto) {
        ReviewResponseDto updatedReview = reviewService.updateReview(id, reviewResponseDto);
        return ResponseEntity.ok(updatedReview);
    }
    // 특정 리뷰 조회
    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponseDto> getReviewById(@PathVariable Long id) {
        ReviewResponseDto review = reviewService.getReviewById(id);
        return ResponseEntity.ok(review);
    }

    // 특정 장소의 모든 리뷰 조회
    @GetMapping("/place/{placeId}")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByPlaceId(@PathVariable Long placeId) {
        List<ReviewResponseDto> reviews = reviewService.getReviewsByPlaceId(placeId);
        return ResponseEntity.ok(reviews);
    }

    // 리뷰 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
