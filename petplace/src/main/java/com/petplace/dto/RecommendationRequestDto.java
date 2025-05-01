package com.petplace.dto;

import com.petplace.entity.Places;
import com.petplace.entity.User;
import lombok.Data;
import java.util.List;

@Data
public class RecommendationRequestDto {
    private User userId;
    private List<RecentViewDto> recentViews;

    @Data
    public static class RecentViewDto {
        private  Long placeId;
        private String city;
    }
}
