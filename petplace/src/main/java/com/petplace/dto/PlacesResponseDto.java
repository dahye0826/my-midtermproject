package com.petplace.dto;

import com.petplace.entity.Places;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlacesResponseDto {
    private Long placeId;
    private String placeName;
    private String placeImage;
    private String industryMain;
    private String city;
    private String district;
    private String roadAddress;
    private Double latitude;
    private Double longitude;
    private String openingHours;
    private String closedDay;
    private String petRestrictions;
    private String indoor;
    private String outdoor;
    private String description;
    private String lastUpdated;

    public static PlacesResponseDto fromEntity(Places place) {
        return new PlacesResponseDto(
                place.getPlaceId(),
                place.getPlaceName(),
                place.getPlaceImage(),
                place.getIndustryMain(),
                place.getCity(),
                place.getDistrict(),
                place.getRoadAddress(),
                place.getLatitude(),
                place.getLongitude(),
                place.getOpeningHours(),
                place.getClosedDay(),
                place.getPetRestrictions(),
                place.getIndoor(),
                place.getOutdoor(),
                place.getDescription(),
                place.getLastUpdated().toLocalDate().toString()
        );
    }
}