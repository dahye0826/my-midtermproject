package com.petplace.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long PostId;
    private String content;

    //지연 로딩: post 엔티티를 조회할때 바로 정보를 가져오지 않는다.
    //post.getUser()를 호출하는 순간에 DB에서 진짜 User 정보를 조회
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // 실제 FK 컬럼명
    private User user;

    private String title ;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime  updatedAt;

    @Column(name = "view_count")
    private int viewCount;

    @Column(name = "comment_count")
    private int commentCount;



}
