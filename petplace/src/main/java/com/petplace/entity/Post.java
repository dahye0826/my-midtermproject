package com.petplace.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name = "posts")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;
    private String content;

    //지연 로딩: post 엔티티를 조회할때 바로 정보를 가져오지 않는다.
    //즉시 로딩: 한 번에 다불러옴
    //post.getUser()를 호출하는 순간에 DB에서 진짜 User 정보를 조회
//    FetchType.EAGER
    // JPA N+1문제를 가지고 있음 이거를 어떻게 할건지
    // 단점이 무엇인가? 이거는 공부하세요
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // 실제 FK 컬럼명
    @NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    private User user;

    private String title ;

    @Column(name = "view_count")
    private int viewCount;

    @Column(name = "comment_count")
    private int commentCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Places place;

    @OneToMany(mappedBy="post", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<PostImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

}
