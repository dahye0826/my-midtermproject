package com.petplace.controller;

import com.petplace.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/community")
public class PostController {

    private final PostService postService;

    // http://localhost:9000/api/community?page=1&size=10
    @GetMapping
    public ResponseEntity<?> getPosts(@RequestParam int page,
                                      @RequestParam(required = false) String search,
                                      @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(postService.getPosts(search, page, size));
    }

    @PostMapping
    public ResponseEntity<?> createpost(
            @RequestPart("postTitle") String title,
            @RequestPart("postContent") String content,
            @RequestPart("placeId") Long placeId,
            @RequestPart(value = "postImages", required = false) List<MultipartFile> images){

        postService.savePostWithImages(title, content,placeId,images);
        return ResponseEntity.ok("등록 완료");
    }

}