package com.petplace.dto;

import com.petplace.entity.Favorites;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoritesResponseDto {
    private Long favoriteId;
    private Long userId;
    private Long placeId;
    private String addedDate;

    public static FavoritesResponseDto fromEntity(Favorites favorites) {
        return new FavoritesResponseDto(
                favorites.getFavoriteId(),
                favorites.getUserId(),
                favorites.getPlaceId(),
                favorites.getAddedDate().toString()
        );
    }
}