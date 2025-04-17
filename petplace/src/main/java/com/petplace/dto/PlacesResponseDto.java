package com.petplace.dto;

import com.petplace.dto.VisitedPlacesResponseDto;
import com.petplace.entity.Places;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private String fullAddress; // Added for frontend display
    private Double latitude;
    private Double longitude;
    private String openingHours;
    private String closedDay;
    private String petRestrictions;
    private String indoor;
    private String outdoor;
    private String description;
    private String lastUpdated;
    private String parkingAvailable;
    private String admissionFee;
    private String placePhone;
    private List<VisitedPlacesResponseDto> visitedPlaces; // Changed from reviews to visitedPlaces

    public static PlacesResponseDto fromEntity(Places place) {
        // Use a different constructor pattern to avoid null pointer exceptions
        PlacesResponseDto dto = new PlacesResponseDto();

        dto.setPlaceId(place.getPlaceId());
        dto.setPlaceName(place.getPlaceName());
        dto.setPlaceImage(place.getPlaceImage());
        dto.setIndustryMain(place.getIndustryMain());
        dto.setCity(place.getCity());
        dto.setDistrict(place.getDistrict());
        dto.setRoadAddress(place.getRoadAddress());
        dto.setFullAddress(place.getFullAddress());
        dto.setLatitude(place.getLatitude());
        dto.setLongitude(place.getLongitude());
        dto.setOpeningHours(place.getOpeningHours());
        dto.setClosedDay(place.getClosedDay());
        dto.setPetRestrictions(place.getPetRestrictions());
        dto.setIndoor(place.getIndoor());
        dto.setOutdoor(place.getOutdoor());
        dto.setDescription(place.getDescription());

        // Handle potential null lastUpdated
        if (place.getLastUpdated() != null) {
            dto.setLastUpdated(place.getLastUpdated().toLocalDate().toString());
        }

        dto.setParkingAvailable(place.getParkingAvailable());
        dto.setAdmissionFee(place.getAdmissionFee());
        dto.setPlacePhone(place.getPlacePhone());

        return dto;
    }
}