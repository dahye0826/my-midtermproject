package com.petplace.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SignupRequestDto {
    private String userName;
    private String email;
    private String password;
    private String userPhone;
    private String userAddress;
    private LocalDate birthdate;
    private String petName;
    private String type;
    private String breed;
    private Integer petAge;
}