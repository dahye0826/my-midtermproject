package com.petplace.dto;

import com.petplace.entity.VisitedPlaces;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitedPlacesResponseDto {
    private Long visitId;
    private Long userId;
    private Long placeId;
    private String visitDate;
    private Integer rating;
    private String note;
    private String createdAt;

    public static VisitedPlacesResponseDto fromEntity(VisitedPlaces visitedPlace) {
        return new VisitedPlacesResponseDto(
                visitedPlace.getVisitId(),
                visitedPlace.getUserId(),
                visitedPlace.getPlaceId(),
                visitedPlace.getVisitDate().toString(),
                visitedPlace.getRating(),
                visitedPlace.getNote(),
                visitedPlace.getCreatedAt().toString()
        );
    }
}