package com.petplace.repository;

import com.petplace.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPostPostIdOrderByCreateAtDesc(Long PostId);
}
