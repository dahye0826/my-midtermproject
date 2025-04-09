package com.petplace.repository;

import com.petplace.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query("SELECT r FROM Report r WHERE " +
            "(:targetType IS NULL OR r.targetType = :targetType) AND " +
            "(:postId IS NULL OR r.postId = :postId) AND " +
            "(:userId IS NULL OR r.userId = :userId)")
    Page<Report> findReportsWithFilters(String targetType, Long postId, Long userId, Pageable pageable);

    Page<Report> findByReasonContaining(String keyword, Pageable pageable);
}