package com.petplace.repository;

import com.petplace.entity.VisitedPlaces;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitedPlacesRepository extends JpaRepository<VisitedPlaces, Long> {

    Page<VisitedPlaces> findByUserId(String userId, Pageable pageable);

    @Query("SELECT v FROM VisitedPlaces v WHERE " +
            "(:userId IS NULL OR v.userId = :userId) AND " +
            "(:placeId IS NULL OR v.placeId = :placeId)")
    Page<VisitedPlaces> findVisitedPlacesWithFilters(Long userId, Long placeId, Pageable pageable);
}