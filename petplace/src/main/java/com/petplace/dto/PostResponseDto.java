package com.petplace.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

public class PostResponseDto {
    private Long postId;
    private String title;
    private Long userId;
    private String userName;
    private String  createdAt;
    private int viewCount;
    private int commentCount;
    private String content;
    private List<String> imageUrls;
    private Long placeId;
    private String placeName;

}
