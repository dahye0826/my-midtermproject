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
    private String userName;

    public CommentResponseDto(Comment comment) {
            this.commentId = comment.getCommentId();
            this.content = comment.getContent();
            this.createdAt = comment.getCreatedAt();
            this.updatedAt = comment.getUpdatedAt();
            this.userId = comment.getUser().getUserId();
            this.postId = comment.getPost().getPostId();
            this.userName = comment.getUser().getUserName();
        }


    }

