package com.petplace.service;

import com.petplace.dto.CommentResponseDto;
import com.petplace.entity.Comment;
import com.petplace.entity.Post;
import com.petplace.repository.CommentRepository;
import com.petplace.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;


    public CommentResponseDto createComment(CommentResponseDto dto) {
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(()-> new IllegalArgumentException("게시글 없음"));
        Comment comment = new Comment();
        comment.setContent(dto.getContent());
        comment.setPost(post);
        comment.setCreateAt(LocalDateTime.now());

        Comment saved = commentRepository.save(comment);

        return CommentResponseDto.builder()
                .commentId(saved.getCommentId())
                .content(saved.getContent())
                .createdAt(saved.getCreateAt().toString())
                .postId(saved.getPost().getPostId())
                .build();

    }
}
