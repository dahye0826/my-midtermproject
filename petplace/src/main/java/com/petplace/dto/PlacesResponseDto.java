package com.petplace.dto;

import com.petplace.entity.Places;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 장소 응답 DTO
 * 클라이언트에 전송할 장소 정보를 담는 데이터 전송 객체
 */
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

    /**
     * Places 엔티티를 DTO로 변환하는 정적 메서드
     * @param place 장소 엔티티
     * @return 변환된 응답 DTO
     */
    public static PlacesResponseDto fromEntity(Places place) {
        PlacesResponseDto dto = new PlacesResponseDto();
        dto.setPlaceId(place.getPlaceId());
        dto.setPlaceName(place.getPlaceName());
        dto.setPlaceImage(place.getPlaceImage());

        // 카테고리 표시를 위해 industrySub를 industryMain으로 사용
        // industrySub가 없으면 industryMain 사용 (카페, 식당 등의 구체적인 카테고리 표시)
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

        // 최종 업데이트 날짜 포맷팅
        if (place.getLastUpdated() != null) {
            dto.setLastUpdated(place.getLastUpdated().toLocalDate().toString());
        }

        return dto;
    }
}