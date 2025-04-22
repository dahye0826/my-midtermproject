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
    private String userName;



    public static VisitedPlacesResponseDto fromEntity(VisitedPlaces entity, String userName) {
        VisitedPlacesResponseDto dto = new VisitedPlacesResponseDto();
        dto.setVisitId(entity.getVisitId());
        dto.setUserId(entity.getUser().getUserId());
        dto.setPlaceId(entity.getPlace().getPlaceId());
        dto.setVisitDate(entity.getVisitDate().toString());
        dto.setRating(entity.getRating());
        dto.setNote(entity.getNote());
        dto.setCreatedAt(entity.getCreatedAt().toString());
        dto.setUserName(userName);


        return dto;
    }

    // 🔧 userName까지 받도록 수정 <이준일 - 오류나서 수정(수정한 사람: 유다혜)>
//    public static VisitedPlacesResponseDto fromEntity(VisitedPlaces visitedPlace, String userName) {
//        return new VisitedPlacesResponseDto(
//                visitedPlace.getVisitId(),
//                visitedPlace.getUser().getUserId(),
//                visitedPlace.getPlace().getPlaceId(),
//                visitedPlace.getVisitDate().toString(),
//                visitedPlace.getRating(),
//                visitedPlace.getNote(),
//                visitedPlace.getCreatedAt().toString(),
//                userName
//        );
//    }
}
