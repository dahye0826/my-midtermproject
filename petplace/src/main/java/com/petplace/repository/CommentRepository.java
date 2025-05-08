package com.petplace.repository;

import com.petplace.entity.Comment;
import com.petplace.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPost(Post post);
    List<Comment> findByPost_PostId(Long postId);

    int countByPost_PostId(Long postId);
}
