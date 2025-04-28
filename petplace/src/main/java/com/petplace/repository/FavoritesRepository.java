package com.petplace.repository;

import com.petplace.entity.Favorites;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoritesRepository extends JpaRepository<Favorites, Long> {

    Page<Favorites> findByUserId(Long userId, Pageable pageable);

    @Query("SELECT f FROM Favorites f WHERE " +
            "(:userId IS NULL OR f.userId = :userId) AND " +
            "(:placeId IS NULL OR f.placeId = :placeId)")
    Page<Favorites> findFavoritesWithFilters(Long userId, Long placeId, Pageable pageable);
    
    Optional<Favorites> findByUserIdAndPlaceId(Long userId, Long placeId);
}