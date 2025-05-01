package com.petplace.repository;

import com.petplace.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    //findAll은 JpaRepository가 이미 기본으로 제공하기 때문에 findAll(Pageable pageable)를 작성할 필요 없다.

    Page<Post> findByUser_UserId(Long userId, Pageable pageable);
    Page<Post> findByTitleContaining(String keyword,Pageable pageable);
    List<Post> findByTitleContaining(String keyword);

    // 사용자 ID로 게시물 삭제 메소드 추가
    @Transactional
    void deleteByUser_UserId(Long userId);
}