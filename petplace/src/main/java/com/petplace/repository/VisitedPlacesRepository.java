package com.petplace.repository;


import com.petplace.entity.VisitedPlaces;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VisitedPlacesRepository extends JpaRepository<VisitedPlaces, Long> {

    //남궁현,이준일
    List<VisitedPlaces> findByPlace_PlaceId(Long placeId);
    Page<VisitedPlaces> findByUser_UserId(Long userId, Pageable pageable);
    //박병규
    List<VisitedPlaces> findAllByPlace_PlaceId(Long placeId);


    Page<VisitedPlaces> findByPlace_PlaceId(Long placeId, Pageable pageable);
    //수정
    long countByUser_UserId(Long userId);
    //수정
    Optional<VisitedPlaces> findByUser_UserIdAndPlace_PlaceId(Long userId, Long placeId);
}

