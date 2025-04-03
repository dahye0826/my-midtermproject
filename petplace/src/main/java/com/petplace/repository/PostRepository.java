package com.petplace.repository;

import com.petplace.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
    //findAll은 JpaRepository가 이미 기본으로 제공하기 때문에 findAll(Pageable pageable)를 작성할 필요 없다.

    Page<Post> findByTitleContaining(String search,Pageable pageable);
}
