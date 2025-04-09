package com.petplace.service;

import com.petplace.dto.PostResponseDto;
import com.petplace.entity.Post;
import com.petplace.entity.PostImage;
import com.petplace.repository.PostImageRepository;
import com.petplace.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;

    // 게시글 목록 조회 (검색 + 페이징)
    public Page<PostResponseDto> getPosts(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());

        Page<Post> postPage;
        if (search != null && !search.trim().isEmpty()) {
            postPage = postRepository.findByTitleContaining(search, pageable);
        } else {
            postPage = postRepository.findAll(pageable);
        }

        return postPage.map(post -> new PostResponseDto(
                post.getPostId(),
                post.getTitle(),
                post.getUser().getUserName(),
                post.getCreatedAt().toLocalDate().toString(),
                post.getUpdatedAt().toLocalDate().toString(), //수정
                post.getViewCount(),
                post.getCommentCount()
        ));
    }

    // 게시글 + 이미지 저장
    public void savePostWithImages(String title, String content, String location, List<MultipartFile> images,
                                   String locationName, String locationAddress, String lat, String lng) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setPostLocation(location);
        post.setLocationName(locationName);
        post.setLocationAddress(locationAddress);
        post.setLocationLat(lat);
        post.setLocationLng(lng);
        post.setViewCount(0);
        post.setCommentCount(0);

        postRepository.save(post);

        String uploadDir = "C:/petImage/images/post/";

        for (MultipartFile file : images) {
            if (!file.isEmpty()) {
                String originalName = file.getOriginalFilename();
                String fileName = UUID.randomUUID() + "_" + originalName;

                Path filePath = Paths.get(uploadDir + fileName);



                try {
                    Files.write(filePath, file.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                PostImage postImage = new PostImage();
                postImage.setImageUrl("/images/post/" + fileName);
                postImage.setPost(post);
                System.out.println("실제 저장될 image_url: " + postImage.getImageUrl());
                postImageRepository.save(postImage);


            }
        }
    }
}
