package com.petplace.repository;

import com.petplace.constant.TargetType;
import com.petplace.entity.Report;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    boolean existsByTargetIdAndTargetTypeAndUserId(Long targetId, TargetType targetType, Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Report r WHERE r.targetType = :targetType AND r.targetId = :targetId")
    void deleteAllByTargetTypeAndTargetId(@Param("targetType") TargetType targetType, @Param("targetId") Long targetId);


    List<Report> findByUserId(Long userId);

    // 특정 사용자가 제출한 모든 신고 삭제
    @Modifying
    @Transactional
    @Query("DELETE FROM Report r WHERE r.userId = :userId")
    void deleteByUserId(@Param("userId") Long userId);
}