package com.petplace.controller;

import com.petplace.dto.CommentResponseDto;
import com.petplace.entity.Comment;
import com.petplace.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/post/{postId}")
    public
    ResponseEntity <List<CommentResponseDto>>getComments
            (@PathVariable Long postId){
        return ResponseEntity.ok(commentService.getComments(postId));

    }

    @GetMapping("/{commentId}")
    public ResponseEntity <CommentResponseDto> getCommentById(@PathVariable Long commentId){
        Comment comment = commentService.getCommentById(commentId);
        return ResponseEntity.ok(new CommentResponseDto(comment));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity <CommentResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody Map<String, String> request
    ) {
        String content = request.get("content");
        Long userId =Long.valueOf(request.get("userId"));
        Comment updatedComment = commentService.updateComment(commentId, content, userId);

        return ResponseEntity.ok(new CommentResponseDto(updatedComment));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deletePost(@PathVariable Long commentId){
        commentService.deletePost(commentId);
        return ResponseEntity.ok("삭제 완료");
    }


    @PostMapping
    public ResponseEntity<CommentResponseDto> addComment(
           @RequestBody Map<String, Object> request
    ) {

        Long postId = Long.valueOf(request.get("postId").toString());
        String content = request.get("content").toString();
        Long userId = Long.valueOf(request.get("userId").toString());



        CommentResponseDto saved = commentService.createComment(postId, content,userId);
        return ResponseEntity.ok(saved);
    }











}
