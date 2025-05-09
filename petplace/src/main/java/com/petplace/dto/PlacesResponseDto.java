package com.petplace.dto;

import com.petplace.entity.Places;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PlacesResponseDto {
    private Long placeId;
    private String placeName;
    private String placeImage;
    private String industryMain;
    private String city;
    private String district;
    private String roadAddress;
    private String fullAddress;
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
    private List<VisitedPlacesResponseDto> visitedPlaces;
    private String petSize;
    private String[] petSizeCategories;

    public static PlacesResponseDto fromEntity(Places place) {
        PlacesResponseDto dto = new PlacesResponseDto();
        dto.setPlaceId(place.getPlaceId());
        dto.setPlaceName(place.getPlaceName());
        dto.setPlaceImage(place.getPlaceImage());

        // Use industrySub instead of industryMain for category display
        // This will show the specific category (카페, 식당, etc.) instead of "반려동물업"
        dto.setIndustryMain(place.getIndustrySub() != null ? place.getIndustrySub() : place.getIndustryMain());

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
        dto.setParkingAvailable(place.getParkingAvailable());
        dto.setAdmissionFee(place.getAdmissionFee());
        dto.setPlacePhone(place.getPlacePhone());
        dto.setPetSize(place.getPetSize());

        // Format last updated date if available
        if (place.getLastUpdated() != null) {
            dto.setLastUpdated(place.getLastUpdated().toLocalDate().toString());
        }

        // 방문 기록 추가
        if (place.getVisitedPlaces() != null) {
            dto.setVisitedPlaces(
                place.getVisitedPlaces().stream()
                    .map(visit -> VisitedPlacesResponseDto.fromEntity(
                        visit, 
                        visit.getUser().getUserName()
                    ))
                    .collect(Collectors.toList())
            );
        }
        return dto;
    }
}