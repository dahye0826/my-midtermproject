package com.petplace.service;

import com.petplace.dto.UserResponseDto;
import com.petplace.entity.User;
import com.petplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Map<String, Object> getUsers(String search, String role, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("userId").descending());

        Page<User> usersPage = userRepository.findUsersWithFilters(search, role, pageable);

        Page<UserResponseDto> usersDto = usersPage.map(UserResponseDto::fromEntity);

        Map<String, Object> response = new HashMap<>();
        response.put("users", usersDto.getContent());
        response.put("currentPage", usersPage.getNumber() + 1);
        response.put("totalItems", usersPage.getTotalElements());
        response.put("totalPages", usersPage.getTotalPages());

        return response;
    }

    public UserResponseDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return UserResponseDto.fromEntity(user);
    }
}