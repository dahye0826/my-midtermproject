package com.petplace.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petplace.constant.TargetType;
import com.petplace.dto.PostResponseDto;
import com.petplace.entity.*;
import com.petplace.repository.*;
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
    private final UserRepository userRepository;
    private final ReportService reportService;
    private final CommentRepository commentRepository ;


    // 게시글 목록 조회 (검색 + 페이징)
    @Transactional
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
        {
            int commentCount = commentRepository.countByPost_PostId(post.getPostId());
            List<String> imageUrls = post.getImages().stream()
                    .map(PostImage::getImageUrl)
                    .collect(Collectors.toList());
            return new PostResponseDto(
                    post.getPostId(),
                    post.getTitle(),
                    post.getUser().getUserId(),
                    post.getUser().getUserName(),
                    post.getCreatedAt() != null ? post.getCreatedAt().toLocalDate().toString() : "작성일 없음",
                    post.getUpdatedAt() != null ? post.getUpdatedAt().toLocalDate().toString() : "수정일 없음",
                    post.getViewCount(),
                    commentCount,
                    post.getContent(),
                    imageUrls,
                    post.getPlace() != null ? post.getPlace().getPlaceId() : null,
                    post.getPlace() != null ? post.getPlace().getPlaceName() : null,
                    post.getPlace() != null ? post.getPlace().getRoadAddress() : null
            );
        });
    }

    // 게시글 + 이미지 저장
    public void savePostWithImages(String title, String content, Long userId, Long placeId, List<MultipartFile> images) {

        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setViewCount(0);
        post.setCommentCount(0);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        post.setUser(user);



        // 장소가 선택된 경우만 연결
        if (placeId != null) {
            Places place = placesRepository.findById(placeId)
                    .orElseThrow(() -> new RuntimeException("해당 장소를 찾을 수 없습니다."));
            post.setPlace(place);
        }

        // 게시글 먼저 저장
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
                .orElseThrow(() -> new EntityNotFoundException("해당 게시글이 없습니다: " + id));

        //Optional.ofNullable 널값 방지
        post.setViewCount(Optional.ofNullable(post.getViewCount()).orElse(0) + 1);

        Places place = post.getPlace();
        Long placeId = null;
        String placeName = null;
        String roadAddress = null;

        if (place != null) {
            placeId = place.getPlaceId();
            placeName = place.getPlaceName();
            roadAddress = place.getRoadAddress();
        }


        return new PostResponseDto(
                post.getPostId(),
                post.getTitle(),
                post.getUser().getUserId(),
                post.getUser().getUserName(),
                post.getCreatedAt() != null ? post.getCreatedAt().toLocalDate().toString() : "작성일 없음",
                post.getUpdatedAt() != null ? post.getUpdatedAt().toLocalDate().toString() : "수정일 없음",
                post.getViewCount(),
                post.getCommentCount(),
                post.getContent(),
                post.getImages().stream()
                        .map(PostImage::getImageUrl)
                        .collect(Collectors.toList()),
                placeId,
                placeName,
                roadAddress

        );
    }

    @Transactional
    public void updatePost(Long id, String title, String content, Long userId, String remainImagesJson, List<MultipartFile> newImages, Long placeId) {
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
            post.setPlace(null); // 선택한 장소가 없는 경우 장소 제거
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        post.setUser(user);


        // 3. 기존 이미지 중 유지할 이미지 필터링
        List<String> finalRemainList;

        if (remainImagesJson != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                finalRemainList = mapper.readValue(remainImagesJson, new TypeReference<List<String>>() {
                });
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
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시글을 찾을 수 없습니다: " + id));

        List<Comment> comments = commentRepository.findByPost_PostId(id);

        // null 방지
        if (comments == null) {
            comments = new ArrayList<>();
        }

        // 댓글이 있을 때만 반복 처리
        for (Comment comment : comments) {
            reportService.deleteAllByTargetTypeAndTargetId(TargetType.COMMENT, comment.getCommentId());
        }

        // 게시글에 대한 신고 삭제
        reportService.deleteAllByTargetTypeAndTargetId(TargetType.POST, id);

        // 댓글이 있으면 댓글 삭제
        if (!comments.isEmpty()) {
            commentRepository.deleteAll(comments);
        }

        // 마지막으로 게시글 삭제
        postRepository.delete(post);
    }


}



