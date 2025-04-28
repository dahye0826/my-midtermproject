package com.petplace.service;

import com.petplace.dto.PostResponseDto;
import com.petplace.dto.UserResponseDto;
import com.petplace.dto.UserUpdateRequestDto;
import com.petplace.entity.Post;
import com.petplace.entity.PostImage;
import com.petplace.entity.User;
import com.petplace.repository.PostRepository;
import com.petplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final VisitedPlacesService visitedPlacesService;

    public Map<String, Object> getUser(String search, String role, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("userId").descending());
        Page<User> userPage = userRepository.findUserWithFilters(search, role, pageable);
        Page<UserResponseDto> userDto = userPage.map(UserResponseDto::fromEntity);

        Map<String, Object> response = new HashMap<>();
        response.put("user", userDto.getContent());
        response.put("currentPage", userPage.getNumber() + 1);
        response.put("totalItems", userPage.getTotalElements());
        response.put("totalPages", userPage.getTotalPages());
        return response;
    }

    public UserResponseDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return UserResponseDto.fromEntity(user);
    }

    public UserResponseDto updateUser(Long userId, UserUpdateRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (dto.getUserName() != null) user.setUserName(dto.getUserName());
        if (dto.getUserPhone() != null) user.setUserPhone(dto.getUserPhone());
        if (dto.getUserAddress() != null) user.setUserAddress(dto.getUserAddress());
        if (dto.getPetName() != null) user.setPetName(dto.getPetName());
        if (dto.getType() != null) user.setType(dto.getType());
        if (dto.getBreed() != null) user.setBreed(dto.getBreed());
        if (dto.getPetAge() != null) user.setPetAge(dto.getPetAge());

        if (dto.getNewPassword() != null && !dto.getNewPassword().isEmpty()) {
            if (dto.getCurrentPassword() == null || !user.getPassword().equals(dto.getCurrentPassword())) {
                throw new RuntimeException("현재 비밀번호가 일치하지 않습니다.");
            }
            user.setPassword(dto.getNewPassword());
        }

        return UserResponseDto.fromEntity(userRepository.save(user));
    }

    public Map<String, Object> getUserPosts(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());
        Page<Post> postsPage = postRepository.findByUser_UserId(userId, pageable);

        List<PostResponseDto> postDtos = postsPage.getContent().stream()
                .map(post -> {
                    List<String> imageUrls = post.getImages().stream()
                            .map(PostImage::getImageUrl)
                            .collect(Collectors.toList());

                    Long placeId = post.getPlace() != null ? post.getPlace().getPlaceId() : null;
                    String placeName = post.getPlace() != null ? post.getPlace().getPlaceName() : null;

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
                            imageUrls,
                            placeId,
                            placeName
                    );
                })
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("posts", postDtos);
        response.put("currentPage", postsPage.getNumber() + 1);
        response.put("totalItems", postsPage.getTotalElements());
        response.put("totalPages", postsPage.getTotalPages());
        return response;
    }

//남궁현
public Map<String, Object> getUserVisitedPlaces(Long userId, int page, int size) {
    return visitedPlacesService.getVisitedPlacesByUserId(userId, page, size);
}

public void deleteUser(Long userId) {
    userRepository.deleteById(userId);
}
}