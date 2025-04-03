package com.petplace.controller;

import com.petplace.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/community")
public class PostController {
    private final PostService postService;
    //http://localhost:9000/api/community?page=1&size=10
    @GetMapping
    public ResponseEntity<?> getPosts(@RequestParam int page,
                                      @RequestParam(required = false) String search,
                                      @RequestParam(defaultValue = "10")int size){

        return ResponseEntity.ok(postService.getPosts(search, page, size));
    }

}
