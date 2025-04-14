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
    public ResponseEntity<?> getPosts(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(required = false) String search,
                                      @RequestParam(defaultValue = "10") int size) {
        if (page < 0) {
            page = 0;
        }

        return ResponseEntity.ok(postService.getPosts(search, page, size));
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> createpost(
            @RequestPart("postTitle") String title,
            @RequestPart("postContent") String content,
            @RequestPart(value="placeId", required = false) Long placeId,
            @RequestPart(value = "postImages", required = false) List<MultipartFile> images){

        postService.savePostWithImages(title, content,placeId,images);
        return ResponseEntity.ok("등록 완료");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostDetail(@PathVariable Long id){
        return ResponseEntity.ok(postService.getPostDetail(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?>  updatePost(
            @PathVariable Long id,
            @RequestPart("postTitle") String postTitle,
            @RequestPart("postContent") String postContent,
            @RequestPart(value = "remainImages", required = false) String remainImagesJson,
            @RequestPart(value = "postImages", required = false) List<MultipartFile> postImages){

        postService.updatePost(id,postTitle,postContent,
                remainImagesJson,postImages);
        return ResponseEntity.ok("수정 완료");



    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return ResponseEntity.ok("삭제 완료");
    }

}