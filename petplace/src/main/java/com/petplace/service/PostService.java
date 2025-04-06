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
<<<<<<< HEAD
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

=======

>>>>>>> origin/master
@Service
@RequiredArgsConstructor
public class PostService{
    private final PostRepository postRepository;
    //“Pageable: 어떤 페이지를 불러올지”에 대한 정보를 담는 인터페이스
    //Pageable 객체를 만들 때 사용하는 팩토리 메서드
    //page-1 0을 뺌 (Spring Data JPA의 PageRequest는 0부터 시작)
    public Page<PostResponseDto> getPosts(String search, int page, int size){
        Pageable pageable = PageRequest.of(page-1,size, Sort.by("createdAt").descending());

        Page<Post> postPage;
        if (search != null && !search.trim().isEmpty()) {
            postPage = postRepository.findByTitleContaining(search, pageable);
        } else {
            postPage = postRepository.findAll(pageable);
        }

        return postPage.map(post -> new PostResponseDto(
                post.getPostId(),
                post.getTitle(),
                post.getUser().getName(),
                post.getCreatedAt().toLocalDate().toString().split("T")[0],
                post.getViewCount(),
                post.getCommentCount()
        ));
<<<<<<< HEAD
    }
    private final PostImageRepository postImageRepository;
    public void savePostWithImages(String title, String content, String location, List<MultipartFile> images,
                         String locationName,String locationAddress,String lat,String lng){

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
                    e.printStackTrace(); // 또는 로깅
                }


                PostImage postImage = new PostImage();
                postImage.setImageUrl("/images/post/" + fileName);
                postImage.setPost(post);
                postImageRepository.save(postImage);
            }
        }

    }


=======
    }
>>>>>>> origin/master
}
