package com.petplace.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petplace.dto.PostResponseDto;
import com.petplace.entity.Places;
import com.petplace.entity.Post;
import com.petplace.entity.PostImage;
import com.petplace.repository.PlacesRepository;
import com.petplace.repository.PostImageRepository;
import com.petplace.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final PlacesRepository placesRepository;



    // 게시글 목록 조회 (검색 + 페이징)
    public Page<PostResponseDto> getPosts(String search, int page, int size) {
        int safePage = Math.max(page - 1, 0);
        Pageable pageable = PageRequest.of(safePage, size, Sort.by("createdAt").descending());

        Page<Post> postPage;
        if (search != null && !search.trim().isEmpty()) {
            postPage = postRepository.findByTitleContaining(search, pageable);
        } else {
            postPage = postRepository.findAll(pageable);
        }




        return postPage.map(post ->
        {List<String> imageUrls = post.getImages().stream()
                        .map(PostImage::getImageUrl)
                        .collect(Collectors.toList());
           return new PostResponseDto(
                post.getPostId(),
                post.getTitle(),
                //post.getUser().getUserName(),
                post.getUser() != null ? post.getUser().getUserName() : "익명",
                post.getCreatedAt() != null ? post.getCreatedAt().toLocalDate().toString() : "작성일 없음",
                post.getUpdatedAt() != null ? post.getUpdatedAt().toLocalDate().toString() : "수정일 없음",
                post.getViewCount(),
                post.getCommentCount(),
                post.getContent(),
                imageUrls,
                post.getPlace() != null ? post.getPlace().getPlaceId() : null,
                post.getPlace() != null ? post.getPlace().getPlaceName() : null
         );
        });
    }

    // 게시글 + 이미지 저장
    public void savePostWithImages(String title, String content,  String placeName, List<MultipartFile> images) {
        System.out.println("🔍 받은 장소 이름: " + placeName); // ✅ 여기에 추가!
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setViewCount(0);
        post.setCommentCount(0);

        if (placeName != null && !placeName.trim().isEmpty()) {
            placesRepository.findByPlaceName(placeName).ifPresent(post::setPlace);
        }

        postRepository.save(post);

        String uploadDir = "C:/petImage/images/post/";

        if (images != null) {
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
                    postImageRepository.save(postImage);
                }
            }
        }

    }

    @Transactional
    public PostResponseDto getPostDetail(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("해당 게시글이 없습니다: " + id));

        //Optional.ofNullable 널값 방지
        post.setViewCount(Optional.ofNullable(post.getViewCount()).orElse(0) + 1);

        Places place = post.getPlace();
        Long placeId = null;
        String placeName = null;

        if (place != null) {
            placeId = place.getPlaceId();
            placeName = place.getPlaceName();
        }

        System.out.println("🔍 받은 장소 이름: " + placeName); // ✅ 여기에 추가!

        return new PostResponseDto(
                post.getPostId(),
                post.getTitle(),
                post.getUser() != null ? post.getUser().getUserName() : "익명",
                post.getCreatedAt() != null ? post.getCreatedAt().toLocalDate().toString() : "작성일 없음",
                post.getUpdatedAt() != null ? post.getUpdatedAt().toLocalDate().toString() : "수정일 없음",
                post.getViewCount(),
                post.getCommentCount(),
                post.getContent(),
                post.getImages().stream()
                        .map(PostImage::getImageUrl)
                        .collect(Collectors.toList()),
                placeId,
                placeName

        );
    }
    @Transactional
    public void updatePost(Long id, String title, String content, String remainImagesJson, List<MultipartFile> newImages, Long placeId) {
        // 1. 게시글 조회
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("게시물 수정 실패: ID " + id));

        // 2. 게시글 정보 수정
        post.setTitle(title);
        post.setContent(content);
        if (placeId != null) {
            Places place = placesRepository.findById(placeId)
                    .orElseThrow(() -> new EntityNotFoundException("장소 없음: " + placeId));
            post.setPlace(place);
        } else {
            post.setPlace(null); // 필요 시 null로 초기화
        }



        // 3. 기존 이미지 중 유지할 이미지 필터링
        List<String> finalRemainList;

        if (remainImagesJson != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                finalRemainList = mapper.readValue(remainImagesJson, new TypeReference<List<String>>() {});
            } catch (JsonProcessingException e) {
                throw new RuntimeException("이미지 파싱 오류", e);
            }
        } else {
            finalRemainList = new ArrayList<>();
        }


        List<PostImage> updatedImages = post.getImages().stream()
                .filter(img -> finalRemainList.contains(img.getImageUrl()))
                .collect(Collectors.toList());
        // 4. 새 이미지 저장 및 추가
        if (newImages != null) {
            for (MultipartFile file : newImages) {
                if (!file.isEmpty()) {
                    try {
                        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
                        Path path = Paths.get("C:/petImage/images/post/" + filename);
                        Files.write(path, file.getBytes());

                        PostImage newImage = new PostImage();
                        newImage.setImageUrl("/images/post/" + filename);
                        newImage.setPost(post);
                        updatedImages.add(newImage);
                    } catch (IOException e) {
                        throw new RuntimeException("이미지 저장 중 오류 발생", e);
                    }
                }
            }
        }

        // 5. 이미지 교체 (기존 이미지 제거 → 새로운 이미지 리스트 추가)
        post.getImages().clear();
        post.getImages().addAll(updatedImages);

        // 6. 저장
        postRepository.save(post);
    }


    @Transactional
    public void deletePost(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("해당 게시글을 찾을 수 없습니다: " + id));
        postRepository.delete(post);

        }


    }



