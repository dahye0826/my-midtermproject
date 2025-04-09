package com.petplace.repository;

import com.petplace.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findByUserNameContaining(String keyword, Pageable pageable);

    @Query("SELECT u FROM User u WHERE " +
            "(:search IS NULL OR LOWER(u.userName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
            "(:role IS NULL OR u.role = :role)")
    Page<User> findUsersWithFilters(String search, String role, Pageable pageable);
}