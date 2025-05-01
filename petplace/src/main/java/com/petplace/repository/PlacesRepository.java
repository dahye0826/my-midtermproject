package com.petplace.repository;

import com.petplace.entity.Places;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlacesRepository extends JpaRepository<Places, Long> {
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

    @Query("SELECT DISTINCT p.city FROM Places p WHERE p.city IS NOT NULL ORDER BY p.city")
    List<String> findDistinctCities();

    @Query("SELECT DISTINCT p.industryMain FROM Places p WHERE p.industryMain IS NOT NULL ORDER BY p.industryMain")
    List<String> findDistinctIndustries();

    List<Places> findByPlaceNameContainingIgnoreCaseOrRoadAddressContainingIgnoreCase(String placeName, String roadAddress);
}