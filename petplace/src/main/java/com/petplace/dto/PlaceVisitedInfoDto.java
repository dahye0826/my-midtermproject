package com.petplace.dto;

import com.petplace.entity.Places;
import com.petplace.entity.VisitedPlaces;
import lombok.Data;

// com.petplace.dto.PlaceVisitedInfoDto
@Data
public class PlaceVisitedInfoDto {

    private String placeName;
    private String placeImage;
    private String address;
    private String city;
    private String district;
    private String petRestrictions;
    private String description;
    private Double latitude;
    private Double longitude;


        public static PlaceVisitedInfoDto fromEntity(VisitedPlaces visitedPlace) {
        PlaceVisitedInfoDto dto = new PlaceVisitedInfoDto();

        if (visitedPlace.getPlace() != null) {
            Places place = visitedPlace.getPlace();
            dto.setPlaceName(place.getPlaceName());
            dto.setPlaceImage(place.getPlaceImage());
            dto.setAddress(place.getRoadAddress());
            dto.setCity(place.getCity());
            dto.setDistrict(place.getDistrict());
            dto.setPetRestrictions(place.getPetRestrictions());
            dto.setDescription(place.getDescription());
            dto.setLatitude(place.getLatitude());
            dto.setLongitude(place.getLongitude());
        }

        return dto;
    }
}
