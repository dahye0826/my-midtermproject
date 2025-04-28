package com.petplace.repository;
import com.petplace.entity.PlaceView;
import com.petplace.entity.Places;
import com.petplace.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PlaceViewRepository extends JpaRepository<PlaceView, Long> {
    // Method renamed to match new field names in PlaceView entity
    List<PlaceView> findTop3ByUser_UserIdOrderByViewedAtDesc(Long userId);
    
    // 사용자의 모든 조회 기록을 가져오는 메서드
    List<PlaceView> findByUser_UserIdOrderByViewedAtDesc(Long userId);

    @Query("SELECT p FROM Places p WHERE " +
            "(:indoor IS NULL OR p.indoor = :indoor) AND " +
            "(:outdoor IS NULL OR p.outdoor = :outdoor) AND " +
            "(:city IS NULL OR p.city = :city) AND " +
            "(:petRestrictions IS NULL OR p.petRestrictions = :petRestrictions)")
    List<Places> findPlacesByCommonFeatures(@Param("indoor") String indoor,
                                            @Param("outdoor") String outdoor,
                                            @Param("city") String city,
                                            @Param("petRestrictions") String petRestrictions);
}