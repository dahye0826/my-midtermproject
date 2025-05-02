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
    @GetMapping
    public ResponseEntity<?> getPosts(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(required = false) String search,
                                      @RequestParam(defaultValue = "10") int size) {
        if (page < 0) {
            page = 0;
        }
        return ResponseEntity.ok(postService.getPosts(search, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostDetail(@PathVariable Long id){
        return ResponseEntity.ok(postService.getPostDetail(id));
    }


    @PostMapping
    public ResponseEntity<?> createPost(
            @RequestParam("postTitle") String title,
            @RequestParam("postContent") String content,
            @RequestParam("userId") Long userId,
            @RequestParam(value = "placeId", required = false) Long placeId,
            @RequestParam(value = "postImages", required = false) List<MultipartFile> images
    ) {
        postService.savePostWithImages(
                title, content,userId, placeId,images
        );
        return ResponseEntity.ok("등록 완료");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return ResponseEntity.ok("삭제 완료");
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<?> updatePostWithMultipart(
            @PathVariable Long id,
            @RequestParam("postTitle") String postTitle,
            @RequestParam("postContent") String postContent,
            @RequestParam("userId") Long userId,
            @RequestParam(value = "remainImages", required = false) String remainImagesJson,
            @RequestParam(value = "postImages", required = false) List<MultipartFile> postImages,
            @RequestParam(value = "placeId", required = false) Long placeId
    ) {
        postService.updatePost(id, postTitle, postContent,userId, remainImagesJson, postImages, placeId);
        return ResponseEntity.ok("수정 완료");
    }

}