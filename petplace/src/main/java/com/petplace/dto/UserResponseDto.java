package com.petplace.dto;

import com.petplace.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Long userId;
    private String userName;
    private String email;
    private String userPhone;
    private String userAddress;
    private String birthdate;
    private String profile;
    private String petName;
    private String type;
    private String breed;
    private Integer petAge;
    private String createdAt;
    private String role;

    public static UserResponseDto fromEntity(User user) {
        return new UserResponseDto(
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                user.getUserPhone(),
                user.getUserAddress(),
                user.getBirthdate().toString(),
                user.getProfile(),
                user.getPetName(),
                user.getType(),
                user.getBreed(),
                user.getPetAge(),
                user.getCreatedAt().toString(),
                user.getRole()
        );
    }
}