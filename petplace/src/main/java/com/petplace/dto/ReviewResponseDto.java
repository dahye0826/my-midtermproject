// ReviewResponseDto.java
package com.petplace.dto;

import com.petplace.entity.Review;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewResponseDto {

    private Long id;
    private Long userId;
    private Long placeId;
    private Double rating;
    private String comment;
    private LocalDateTime createdAt;

    // Review 엔티티를 DTO로 변환하는 메서드
    public static ReviewResponseDto fromEntity(Review review) {
        // ReviewResponseDto 객체 생성
        ReviewResponseDto dto = new ReviewResponseDto();

        // Review 엔티티에서 필요한 값들을 DTO로 설정
        dto.id = review.getId();
        dto.userId = review.getUser() != null ? review.getUser().getUserId() : null;
        dto.placeId = review.getPlace() != null ? review.getPlace().getPlaceId() : null;
        dto.rating = review.getRating();
        dto.comment = review.getComment();
        dto.createdAt = review.getCreatedAt();

        return dto;
    }
}
