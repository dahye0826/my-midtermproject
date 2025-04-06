package com.petplace.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name = "posts")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;
    private String content;

    //지연 로딩: post 엔티티를 조회할때 바로 정보를 가져오지 않는다.
    //post.getUser()를 호출하는 순간에 DB에서 진짜 User 정보를 조회
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // 실제 FK 컬럼명
    private User user;

    private String title ;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;


    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @Column(name = "view_count")
    private int viewCount;

    @Column(name = "comment_count")
    private int commentCount;

    @Column(name ="post_location")
    private String postLocation;// 프론트에서 검색한 키워드
    @Column(name ="location_name")
    private String locationName;     // 카카오 장소 이름
    @Column(name ="location_address")
    private String locationAddress;  // 카카오 장소 주소
    @Column(name ="location_lat")
    private String locationLat;      // 위도
    @Column(name ="location_lng")
    private String locationLng;      // 경도

    @OneToMany(mappedBy="post", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<PostImage> images = new ArrayList<>();

}
