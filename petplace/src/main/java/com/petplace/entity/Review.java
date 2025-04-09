package com.petplace.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "review")
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // User와의 관계 설정 (외래 키 user_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // 외래 키 user_id와 매핑
    private User user;

    // PetFriendlyPlace와의 관계 설정 (외래 키 place_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Places place;

    // 리뷰 평점 (0~5 등 범위로 설정 가능)
    @Column(name = "rating", nullable = false)
    private Double rating;

    // 리뷰 내용 (null 가능)
    @Column(name = "comment", nullable = true) // 댓글은 필수가 아니므로 null 가능
    private String comment;

    // 리뷰 작성 시간
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // 생성 시점 자동 설정
    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now(); // 생성 시간 자동 설정
        }
    }

}
