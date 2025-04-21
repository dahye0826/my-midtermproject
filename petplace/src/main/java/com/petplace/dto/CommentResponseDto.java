package com.petplace.dto;

import com.petplace.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDto {
    private Long commentId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime  updatedAt;
    private Long userId;
    private Long postId;
    private String username;



    public CommentResponseDto(Comment comment) {
            this.commentId = comment.getCommentId();
            this.content = comment.getContent();
            this.createdAt = comment.getCreatedAt();
            this.updatedAt = comment.getUpdatedAt();
//            this.userId = comment.getUser().getUserId();
            this.postId = comment.getPost().getPostId();
//            this.username = comment.getUser().getUserName();
        if (comment.getUser() != null) {
            this.userId = comment.getUser().getUserId();
            this.username = comment.getUser().getUserName();
        } else {
            this.userId = null;
            this.username = "익명";
        }

        }


    }

