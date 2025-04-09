package com.petplace.repository;

import com.petplace.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByPlace_PlaceId(Long placeId);
}

