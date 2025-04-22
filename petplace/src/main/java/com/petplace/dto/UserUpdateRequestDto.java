package com.petplace.dto;

import lombok.Data;

@Data
public class UserUpdateRequestDto {
    private String userName;
    private String userPhone;
    private String userAddress;
    private String currentPassword;
    private String newPassword;
    private String petName;
    private String type;
    private String breed;
    private Integer petAge;
}