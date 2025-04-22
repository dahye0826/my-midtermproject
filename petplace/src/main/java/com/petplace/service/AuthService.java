package com.petplace.service;

import com.petplace.dto.LoginRequestDto;
import com.petplace.dto.SignupRequestDto;
import com.petplace.dto.UserResponseDto;
import com.petplace.entity.User;
import com.petplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    public Map<String, Object> login(LoginRequestDto loginRequestDto) {
        Map<String, Object> response = new HashMap<>();

        Optional<User> userOptional = userRepository.findByEmail(loginRequestDto.getEmail());

        if (userOptional.isPresent() && userOptional.get().getPassword().equals(loginRequestDto.getPassword())) {
            User user = userOptional.get();

            response.put("success", true);
            response.put("userId", user.getUserId());
            response.put("userName", user.getUserName());
            response.put("email", user.getEmail());
            response.put("role", user.getRole());
            response.put("profile", user.getProfile());

        } else {
            response.put("success", false);
            response.put("message", "이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        return response;
    }

    @Transactional
    public UserResponseDto signup(SignupRequestDto dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        User user = new User();
        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setUserPhone(dto.getUserPhone());
        user.setUserAddress(dto.getUserAddress());
        user.setBirthdate(dto.getBirthdate() != null ? dto.getBirthdate() : LocalDate.now());
        user.setPetName(dto.getPetName());
        user.setType(dto.getType());
        user.setBreed(dto.getBreed());
        user.setPetAge(dto.getPetAge());
        user.setCreatedAt(LocalDate.now());
        user.setRole("USER");

        return UserResponseDto.fromEntity(userRepository.save(user));
    }
}