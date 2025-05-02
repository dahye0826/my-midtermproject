package com.petplace.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Setter
@Getter
public class PlaceViewRequestDto {
    private Long placeId;
    private Long userId;
    private Long timeSpent;
    private LocalDateTime viewedAt;

}

