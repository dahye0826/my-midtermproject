package com.petplace.test;

import com.petplace.entity.User;
import com.petplace.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class UserTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void insertUserList() {
        User user1 = new User();
        user1.setName("관리자");
        user1.setEmail("admin@naver.com");
        user1.setPassword("Admin@123");
        user1.setPhone("010-1111-2222");
        user1.setAddress("서울 마포구");
        user1.setCreatedAt(LocalDateTime.now());
        userRepository.save(user1);

        User user2 = new User();
        user2.setName("유영석");
        user2.setEmail("bluesky@naver.com");
        user2.setPassword("Bluesky@456");
        user2.setPhone("010-3333-4444");
        user2.setAddress("서울 용산구");
        user2.setCreatedAt(LocalDateTime.now());
        userRepository.save(user2);

        User user3 = new User();
        user3.setName("곰돌이");
        user3.setEmail("gomdori@naver.com");
        user3.setPassword("Gomdori@789");
        user3.setPhone("010-5555-6666");
        user3.setAddress("서울 동대문구");
        user3.setCreatedAt(LocalDateTime.now());
        userRepository.save(user3);
    }
}