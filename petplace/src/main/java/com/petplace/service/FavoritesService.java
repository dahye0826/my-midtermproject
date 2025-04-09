package com.petplace.service;

import com.petplace.dto.FavoritesResponseDto;
import com.petplace.entity.Favorites;
import com.petplace.repository.FavoritesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FavoritesService {

    private final FavoritesRepository favoritesRepository;

    public Map<String, Object> getFavorites(Long userId, Long placeId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("favoriteId").descending());

        Page<Favorites> favoritesPage = favoritesRepository.findFavoritesWithFilters(userId, placeId, pageable);

        Page<FavoritesResponseDto> favoritesDto = favoritesPage.map(FavoritesResponseDto::fromEntity);

        Map<String, Object> response = new HashMap<>();
        response.put("favorites", favoritesDto.getContent());
        response.put("currentPage", favoritesPage.getNumber() + 1);
        response.put("totalItems", favoritesPage.getTotalElements());
        response.put("totalPages", favoritesPage.getTotalPages());

        return response;
    }

    public FavoritesResponseDto getFavoriteById(Long favoriteId) {
        Favorites favorites = favoritesRepository.findById(favoriteId)
                .orElseThrow(() -> new RuntimeException("Favorite not found with id: " + favoriteId));
        return FavoritesResponseDto.fromEntity(favorites);
    }
}