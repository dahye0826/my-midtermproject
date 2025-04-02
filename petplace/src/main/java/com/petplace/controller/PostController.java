package com.petplace.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/community")
public class PostController {

    @GetMapping
    public ResponseEntity<?> getPosts(@RequestParam int page,
                                      @RequestParam(required = false) String search,
                                      @RequestParam(defaultValue = "10")int size){
        return ResponseEntity.ok("게시물 목록 가져옴");
    }

}
