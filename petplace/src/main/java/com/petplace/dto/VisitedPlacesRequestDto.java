package com.petplace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitedPlacesRequestDto {
    private Long userId;
    private Long placeId;
    private Integer rating;
    private String note;
    private String visitDate;
    private String createdAt;
}
