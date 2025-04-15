package com.petplace.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "user")
@Getter @Setter @NoArgsConstructor
public class User {
    @Id
    @Column(name = "user_id", length = 20)
    private Long userId;

    @Column(name = "user_name", length = 30)
    private String userName;

    @Column(length = 50)
    private String email;

    @Column(length = 30)
    private String password;

    @Column(name = "user_phone", length = 20)
    private String userPhone;

    @Column(name = "user_address", length = 100)
    private String userAddress;

    private LocalDate birthdate;

    @Column(length = 20)
    private String profile;

    @Column(name = "pet_name", length = 50)
    private String petName;

    @Column(length = 50)
    private String type;

    @Column(length = 50)
    private String breed;

    @Column(name = "pet_age")
    private Integer petAge;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(length = 20)
    private String role;
}