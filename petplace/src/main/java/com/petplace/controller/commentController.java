package com.petplace.controller;


import com.petplace.dto.CommentResponseDto;
import com.petplace.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class commentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> addComment(@RequestBody CommentResponseDto dto){
        CommentResponseDto response = commentService.createComment(dto) ;
        return ResponseEntity.ok(response);
        //댓글페이지는 한페이지에서 바로 값을 받기때문에 바로 리턴하는 것이다.
    }







}
