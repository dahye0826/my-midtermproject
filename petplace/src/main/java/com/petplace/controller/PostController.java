package com.petplace.controller;

import com.petplace.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor //생성자를 자동으로 만들어줌
@RestController
@RequestMapping("/api/community")
public class PostController {

    private final PostService postService;

    // http://localhost:9000/api/community?page=1&size=10
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
            @RequestParam(value = "placeId", required = false) Long placeId,
            @RequestParam(value = "postImages", required = false) List<MultipartFile> images
    ) {
        postService.savePostWithImages(
                title, content, placeId,images
        );
        return ResponseEntity.ok("등록 완료");
    }


    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updatePost(
            @PathVariable Long id,
            @RequestPart("postTitle") String postTitle,
            @RequestPart("postContent") String postContent,
            @RequestPart(value = "remainImages", required = false) String remainImagesJson,
            @RequestPart(value = "postImages", required = false) List<MultipartFile> postImages,
            @RequestPart(value = "placeId", required = false) Long placeId
    ) {
        try{
            postService.updatePost(id, postTitle, postContent, remainImagesJson, postImages, placeId);
        }catch (Exception e){
            System.out.println(e.getStackTrace());
        }
        return ResponseEntity.ok("수정 완료");
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
            @RequestParam(value = "remainImages", required = false) String remainImagesJson,
            @RequestParam(value = "postImages", required = false) List<MultipartFile> postImages,
            @RequestParam(value = "placeId", required = false) Long placeId
    ) {
        postService.updatePost(id, postTitle, postContent, remainImagesJson, postImages, placeId);
        return ResponseEntity.ok("수정 완료");
    }

}