package com.petplace.service;

import com.petplace.dto.RecommendationRequestDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class RecommendationService {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Integer> getRecommendedPlaces(Long userId, List<RecommendationRequestDto.RecentViewDto> recentViews) {
        String flaskUrl = "http://localhost:5001/recommend";

        // DTO → Map 변환
        List<Map<String, Object>> recentViewsAsMap = recentViews.stream().map(dto -> {
            Map<String, Object> map = new HashMap<>();
            map.put("placeId", dto.getPlaceId());
            map.put("city", dto.getCity());
            return map;
        }).toList();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("userId", userId.toString()); // Flask가 String 기대하면 toString
        requestBody.put("recentViews", recentViewsAsMap);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                flaskUrl,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {}
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            Object rawList = response.getBody().get("recommendedPlaceIds");
            List<Integer> recommended = new ArrayList<>();
            if (rawList instanceof List<?>) {
                for (Object item : (List<?>) rawList) {
                    if (item instanceof Number) {
                        recommended.add(((Number) item).intValue());
                    }
                }
                return recommended;
            }
        }

        return Collections.emptyList(); // 마지막 return 빠진 거 보완
    }
}
