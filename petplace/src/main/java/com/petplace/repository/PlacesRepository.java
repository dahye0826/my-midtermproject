package com.petplace.repository;

import com.petplace.entity.Places;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlacesRepository extends JpaRepository<Places, Long> {
    // Removed the findByPlace_Id method as it doesn't make sense in this context

    Page<Places> findByPlaceNameContaining(String keyword, Pageable pageable);

    @Query("SELECT p FROM Places p WHERE " +
            "(:search IS NULL OR LOWER(p.placeName) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
            "(:industry IS NULL OR " +
            "LOWER(p.industryMain) LIKE LOWER(CONCAT('%', :industry, '%')) OR " +
            "LOWER(p.industryMid) LIKE LOWER(CONCAT('%', :industry, '%')) OR " +
            "LOWER(p.industrySub) LIKE LOWER(CONCAT('%', :industry, '%'))) AND " +
            "(:city IS NULL OR LOWER(p.city) LIKE LOWER(CONCAT('%', :city, '%'))) AND " +
            "(:district IS NULL OR LOWER(p.district) LIKE LOWER(CONCAT('%', :district, '%')))")
    Page<Places> findPlacesWithFilters(String search, String industry, String city, String district, Pageable pageable);

    @Query("SELECT DISTINCT p.city FROM Places p WHERE p.city IS NOT NULL ORDER BY p.city")
    List<String> findDistinctCities();

    @Query("SELECT DISTINCT p.industryMain FROM Places p WHERE p.industryMain IS NOT NULL ORDER BY p.industryMain")
    List<String> findDistinctIndustries();

    @Query(value = """
    SELECT *, 
    (6371000 * acos(
        cos(radians(:lat)) * cos(radians(p.latitude)) *
        cos(radians(p.longitude) - radians(:lng)) +
        sin(radians(:lat)) * sin(radians(p.latitude))
    )) AS distance
    FROM places p
    HAVING distance <= :radius
    ORDER BY distance
    """, nativeQuery = true)
    List<Places> findNearbyPlaces(@Param("lat") double lat,
                                  @Param("lng") double lng,
                                  @Param("radius") double radius);
}

