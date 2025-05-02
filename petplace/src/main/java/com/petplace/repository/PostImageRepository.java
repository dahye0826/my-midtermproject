package com.petplace.repository;

import com.petplace.entity.Post;
import com.petplace.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage,Long> {
    List<PostImage> findByPost(Post post);

}
