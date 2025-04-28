package com.petplace.service;

import com.petplace.dto.PlaceViewRequestDto;
import com.petplace.dto.RecommendationRequestDto;
import com.petplace.entity.Places;
import com.petplace.entity.PlaceView;
import com.petplace.entity.User;
import com.petplace.repository.PlaceViewRepository;
import com.petplace.repository.PlacesRepository;
import com.petplace.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceViewService {

    private final PlaceViewRepository placeViewRepository;
    private final PlacesRepository placeRepository;
    private final UserRepository userRepository;

    public List<RecommendationRequestDto.RecentViewDto> getRecentViewDtos(Long userId) {
        List<PlaceView> recentViews = placeViewRepository.findByUser_UserIdOrderByViewedAtDesc(userId);

        return recentViews.stream().map(view -> {
            RecommendationRequestDto.RecentViewDto dto = new RecommendationRequestDto.RecentViewDto();
            Long placeId = view.getPlace().getPlaceId();
            dto.setPlaceId(placeId);
            dto.setCity(view.getPlace().getCity());
            return dto;
        }).toList();
    }

    @Transactional
    public void savePlaceView(PlaceViewRequestDto request) {
        System.out.println(" [요청 수신] userId: " + request.getUserId() + ", placeId: " + request.getPlaceId());

        Places place = placeRepository.findById(request.getPlaceId())
                .orElseThrow(() -> new IllegalArgumentException("해당 장소가 존재하지 않습니다."));
        System.out.println(" [장소 조회 성공] placeId: " + place.getPlaceId());

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> {
                    System.out.println(" [유저 조회 실패] userId=" + request.getUserId());
                    return new IllegalArgumentException("해당 유저가 존재하지 않습니다.");
                });
        System.out.println(" [유저 조회 성공] userId: " + user.getUserId());

        PlaceView placeView = new PlaceView();
        placeView.setPlace(place);
        placeView.setUser(user);
        placeView.setViewedAt(request.getViewedAt() != null ? request.getViewedAt() : LocalDateTime.now());
        placeView.setTimeSpent(request.getTimeSpent());

        placeViewRepository.save(placeView);
        System.out.println(" [저장 완료] 로그인 유저의 placeView 저장됨!");
    }
}