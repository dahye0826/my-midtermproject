package com.petplace.service;

import com.petplace.constant.TargetType;
import com.petplace.dto.CommentResponseDto;
import com.petplace.entity.Comment;
import com.petplace.entity.Post;
import com.petplace.entity.User;
import com.petplace.repository.CommentRepository;
import com.petplace.repository.PostRepository;
import com.petplace.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ReportService reportService;


    public List<CommentResponseDto> getComments(Long postId){
        //IllegalArgumentException: 잘 못된 입력값이 들어왔을 때 사용
        Post post = postRepository.findById(postId).orElseThrow(()->new IllegalArgumentException("게시글 없음"));

        List<Comment> comments = commentRepository.findByPost(post);
        //comments라는 Comment 객체들의 리스트를 → 하나씩 CommentResponseDto에 담아서 → 다시 리스트로 만든다.
       return comments.stream()
               .map(CommentResponseDto::new)
               .collect(Collectors.toList());
    }

    public Comment getCommentById(Long commentId){
        return commentRepository.findById(commentId)
                .orElseThrow(()->new RuntimeException("댓글을 찾을 수 없습니다."));
    }

    public Comment updateComment(Long commentId, String content, Long userId) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new IllegalArgumentException("댓글 없음"));
        comment.setContent(content);

        User user = userRepository.findById(userId)
                .orElseThrow(()->new EntityNotFoundException("사용자가 존재하지 않습니다"));

        return commentRepository.save(comment);

    }

    @Transactional
    public void deletePost(Long commentId) {
        reportService.deleteAllByTargetTypeAndTargetId(TargetType.COMMENT, commentId);
        Comment comment=commentRepository.findById(commentId)
         .orElseThrow(()-> new EntityNotFoundException("해당 댓글을 찾을 수 없습니다: " + commentId));
        commentRepository.delete(comment);

    }


    @Transactional
    public CommentResponseDto createComment(Long postId, String content, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));

        // 임시 사용자 생성 또는 조회 (실제 환경에서는 userRepository로 조회)
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자가 존재하지 않습니다."));

        Comment comment = Comment.builder()
                .content(content)
                .post(post)
                .user(user)
                .build();

        Comment saved = commentRepository.save(comment);
        return new CommentResponseDto(saved);
    }


}

