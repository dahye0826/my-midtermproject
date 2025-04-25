package com.petplace.repository;

import com.petplace.constant.TargetType;
import com.petplace.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    boolean existsByTargetIdAndTargetTypeAndUserId(Long targetId, TargetType targetType, Long userId);
}