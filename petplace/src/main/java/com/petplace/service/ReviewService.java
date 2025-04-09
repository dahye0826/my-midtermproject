package com.petplace.service;

import com.petplace.dto.ReviewResponseDto;
import com.petplace.entity.Review;
import com.petplace.entity.User;
import com.petplace.entity.Places; // 실제로 Places 클래스를 사용하고 있으므로 임포트 필요
import com.petplace.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService { @Autowired
private ReviewRepository reviewRepository;

    // 특정 장소에 대한 모든 리뷰 조회
    public List<ReviewResponseDto> getReviewsByPlaceId(Long placeId) {
        // ReviewRepository에서 특정 장소의 리뷰들을 조회
        List<Review> reviews = reviewRepository.findByPlace_PlaceId(placeId);  // findByPlace_Id로 매핑

        // 조회된 리뷰들을 ReviewResponseDto 리스트로 변환하여 반환
        return reviews.stream()
                .map(ReviewResponseDto::fromEntity)  // Review 객체를 ReviewResponseDto로 변환
                .collect(Collectors.toList());
    }

    // 특정 리뷰 조회
    public ReviewResponseDto getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review not found with id " + id));
        return ReviewResponseDto.fromEntity(review);
    }

    // 리뷰 작성
    public ReviewResponseDto createReview(ReviewResponseDto reviewResponseDto) {
        Review review = new Review();
        review.setUser(new User()); // 실제 유저를 설정해야 합니다.
        review.setPlace(new Places()); // 실제 장소를 설정해야 합니다.
        review.setRating(reviewResponseDto.getRating());
        review.setComment(reviewResponseDto.getComment());

        reviewRepository.save(review);
        return ReviewResponseDto.fromEntity(review);
    }

    // 리뷰 수정
    public ReviewResponseDto updateReview(Long id, ReviewResponseDto reviewResponseDto) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review not found with id " + id));

        review.setRating(reviewResponseDto.getRating());
        review.setComment(reviewResponseDto.getComment());
        reviewRepository.save(review);

        return ReviewResponseDto.fromEntity(review);
    }

    // 리뷰 삭제
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
