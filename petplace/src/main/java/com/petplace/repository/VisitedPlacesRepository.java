package com.petplace.repository;

import com.petplace.entity.VisitedPlaces;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface VisitedPlacesRepository extends JpaRepository<VisitedPlaces, Long> {

    //남궁현,이준일
    List<VisitedPlaces> findByPlace_PlaceId(Long placeId);
    Page<VisitedPlaces> findByUser_UserId(Long userId, Pageable pageable);
    //박병규
    List<VisitedPlaces> findAllByPlace_PlaceId(Long placeId);
    Page<VisitedPlaces> findAllByOrderByVisitDateDesc(Pageable pageable);

    Page<VisitedPlaces> findByPlace_PlaceId(Long placeId, Pageable pageable);
    //수정
    long countByUser_UserId(Long userId);
    //수정
    Optional<VisitedPlaces> findByUser_UserIdAndPlace_PlaceId(Long userId, Long placeId);

    // 장소별 평균 별점 계산 쿼리
    @Query("SELECT v.place.placeId, AVG(v.rating) as avgRating " +
            "FROM VisitedPlaces v " +
            "GROUP BY v.place.placeId")
    List<Object[]> calculateAverageRatingByPlaceId();

    // 특정 장소의 평균 별점 계산 쿼리
    @Query("SELECT AVG(v.rating) FROM VisitedPlaces v WHERE v.place.placeId = :placeId")
    Double calculateAverageRatingForPlace(@Param("placeId") Long placeId);

    // 사용자 ID로 방문 이력 조회 (deleteByUserId 메소드 구현용)
    List<VisitedPlaces> findByUser_UserId(Long userId);

    // 사용자 ID로 방문 이력 삭제 메소드 추가
    @Transactional
    void deleteByUser_UserId(Long userId);
}