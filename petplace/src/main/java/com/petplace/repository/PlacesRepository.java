package com.petplace.repository;

import com.petplace.entity.Places;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 장소 리포지토리
 * 장소 데이터 접근을 위한 인터페이스
 */
@Repository
public interface PlacesRepository extends JpaRepository<Places, Long> {
    /**
     * 필터를 적용하여 장소 검색
     * 검색어, 산업 카테고리, 도시, 지역구, 반려견 크기 기준으로 검색
     *
     * @param search 검색어
     * @param industry 산업 카테고리
     * @param city 도시
     * @param district 지역구
     * @param petSize 반려견 크기
     * @param pageable 페이징 정보
     * @return 필터링된 장소 페이지
     */
    @Query("SELECT p FROM Places p WHERE " +
            "(:search IS NULL OR LOWER(p.placeName) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
            "(:industry IS NULL OR LOWER(p.industrySub) = LOWER(:industry)) AND " +
            "(:city IS NULL OR LOWER(p.city) LIKE LOWER(CONCAT('%', :city, '%'))) AND " +
            "(:district IS NULL OR LOWER(p.district) LIKE LOWER(CONCAT('%', :district, '%'))) AND " +
            "(:petSize IS NULL OR " +
            "(:petSize = 'small' AND (LOWER(p.petSize) LIKE '%소형%' OR LOWER(p.petSize) LIKE '%모두%' OR LOWER(p.petSize) LIKE '%전체%' OR LOWER(p.petSize) LIKE '%10kg%미만%' OR LOWER(p.petSize) LIKE '%10kg%이하%')) OR " +
            "(:petSize = 'medium' AND (LOWER(p.petSize) LIKE '%중형%' OR LOWER(p.petSize) LIKE '%모두%' OR LOWER(p.petSize) LIKE '%전체%' OR LOWER(p.petSize) LIKE '%10kg%이상%' OR LOWER(p.petSize) LIKE '%10kg%25kg%')) OR " +
            "(:petSize = 'large' AND (LOWER(p.petSize) LIKE '%대형%' OR LOWER(p.petSize) LIKE '%모두%' OR LOWER(p.petSize) LIKE '%전체%' OR LOWER(p.petSize) LIKE '%25kg%이상%')))")
    Page<Places> findPlacesWithFilters(
            String search,
            String industry,
            String city,
            String district,
            String petSize,
            Pageable pageable
    );

    /**
     * 중복 없는 모든 도시 목록 조회
     * @return 도시 목록
     */
    @Query("SELECT DISTINCT p.city FROM Places p WHERE p.city IS NOT NULL ORDER BY p.city")
    List<String> findDistinctCities();

    /**
     * 중복 없는 모든 산업 분류 목록 조회
     * @return 산업 분류 목록
     */
    @Query("SELECT DISTINCT p.industryMain FROM Places p WHERE p.industryMain IS NOT NULL ORDER BY p.industryMain")
    List<String> findDistinctIndustries();

    /**
     * 장소명 또는 도로명 주소로 장소 검색
     * @param placeName 장소명 키워드
     * @param roadAddress 도로명 주소 키워드
     * @return 검색된 장소 목록
     */
    List<Places> findByPlaceNameContainingIgnoreCaseOrRoadAddressContainingIgnoreCase(String placeName, String roadAddress);
}