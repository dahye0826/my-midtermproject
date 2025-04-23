package com.petplace.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data //getter, setter, tostring 등 자동으로 생성해준다.
@AllArgsConstructor // 모든 필드(변수)를 초기화 하는 생성자 자동 생성
public class PostResponseDto {
    private Long postId;
    private String title;
    private Long userId;
    private String userName;
    private String  createdAt;
    private String updatedAt; //수정
    private int viewCount;
    private int commentCount;
    private String content;
    private List<String> imageUrls;


    private Long placeId;
    private String placeName;

}
