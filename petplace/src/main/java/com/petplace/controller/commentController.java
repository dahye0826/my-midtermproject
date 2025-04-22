package com.petplace.controller;

import com.petplace.dto.CommentResponseDto;
import com.petplace.entity.Comment;
import com.petplace.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
 // 깃 테승트 -- 이준일
@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class commentController {
    private final CommentService commentService;

    @GetMapping("/post/{postId}")
    public
    ResponseEntity <List<CommentResponseDto>>getComments
            (@PathVariable Long postId){
        return ResponseEntity.ok(commentService.getComments(postId));

    }

    @PutMapping("/{commentId}")
    public ResponseEntity <CommentResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody Map<String, String> request
    ) {
        String content = request.get("content");
        Comment updatedComment = commentService.updateComment(commentId, content);

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
    ){

        Long postId = Long.valueOf(request.get("postId").toString());
        String content = request.get("content").toString();
//        String userName = request.get("username").toString();


        CommentResponseDto saved = commentService.createComment(postId, content);
        return ResponseEntity.ok(saved);
        }











}
