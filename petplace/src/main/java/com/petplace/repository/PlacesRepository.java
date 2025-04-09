package com.petplace.repository;

import com.petplace.entity.Places;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlacesRepository extends JpaRepository<Places, Long> {

    Page<Places> findByPlaceNameContaining(String keyword, Pageable pageable);

    @Query("SELECT p FROM Places p WHERE " +
            "(:search IS NULL OR LOWER(p.placeName) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
            "(:industry IS NULL OR p.industryMain = :industry) AND " +
            "(:city IS NULL OR p.city = :city) AND " +
            "(:district IS NULL OR p.district = :district)")
    Page<Places> findPlacesWithFilters(String search, String industry, String city, String district, Pageable pageable);
}