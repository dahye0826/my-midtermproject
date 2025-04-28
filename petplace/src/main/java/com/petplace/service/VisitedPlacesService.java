package com.petplace.service;

import com.petplace.constant.TargetType;
import com.petplace.dto.VisitedPlacesRequestDto;

import com.petplace.dto.VisitedPlacesResponseDto;
import com.petplace.entity.Places;
import com.petplace.entity.User;

import com.petplace.entity.VisitedPlaces;

import com.petplace.repository.PlacesRepository;
import com.petplace.repository.UserRepository;

import com.petplace.repository.VisitedPlacesRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitedPlacesService {

    private final PlacesRepository placesRepository ;
    private final VisitedPlacesRepository visitedPlacesRepository;
    private final UserRepository userRepository;
    private final ReportService reportService;

    public List<VisitedPlacesResponseDto> getRecentReviews(int size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "visitDate"));
        List<VisitedPlaces> visitedPlacesList = visitedPlacesRepository.findAll(pageable).getContent();

        return visitedPlacesList.stream()
                .map(entity -> VisitedPlacesResponseDto.fromEntity(entity, entity.getUser().getUserName()))
                .collect(Collectors.toList());
    }

    public List<VisitedPlacesResponseDto> getVisitedPlacesByPlaceId(Long placeId) {
        List<VisitedPlaces> visitedPlaces = visitedPlacesRepository.findByPlace_PlaceId(placeId);

        return visitedPlaces.stream().map(place -> {
            String userName = userRepository.findById(place.getUser().getUserId())
                    .map(User::getUserName)
                    .orElse("익명");
            return VisitedPlacesResponseDto.fromEntity(place, userName);
        }).collect(Collectors.toList());
    }


    @Transactional
    public void createVisitedPlace(VisitedPlacesRequestDto dto) {
        VisitedPlaces vp = new VisitedPlaces();

        vp.setRating(dto.getRating());
        vp.setNote(dto.getNote());

        vp.setVisitDate(LocalDate.parse(dto.getVisitDate()));
        vp.setCreatedAt(LocalDate.parse(dto.getCreatedAt()));

        // 👇 엔터티 변환 필수!!
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));
        Places place = placesRepository.findById(dto.getPlaceId())
                .orElseThrow(() -> new IllegalArgumentException("장소 없음"));

        vp.setUser(user);
        vp.setPlace(place);
//setPlaceId->setplace(place)

        visitedPlacesRepository.save(vp);
    }

    private static final Logger logger = LoggerFactory.getLogger(VisitedPlacesService.class);
    //남궁현
    @Transactional(readOnly = true)
    public Map<String, Object> getVisitedPlacesByUserId(Long userId, int page, int size) {
        int safePage = Math.max(0, page - 1);
        Pageable pageable = PageRequest.of(safePage, size, Sort.by("visitDate").descending());
        Page<VisitedPlaces> pageResult = visitedPlacesRepository.findByUser_UserId(userId, pageable);

        List<VisitedPlacesResponseDto> dtoList = pageResult.getContent().stream().map(visited -> {
            String userName = userRepository.findById(userId)
                    .map(User::getUserName)
                    .orElse("익명");
            return VisitedPlacesResponseDto.fromEntity(visited, userName);
        }).collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("content", dtoList);
        result.put("totalPages", pageResult.getTotalPages());
        result.put("currentPage", pageResult.getNumber() + 1);
        result.put("totalElements", pageResult.getTotalElements());
        return result;
    }

    @Transactional(readOnly = true)
    public long countVisitedPlacesByUserId(Long userId) {
        return visitedPlacesRepository.countByUser_UserId(userId); // 수정
    }

    @Transactional
    public VisitedPlacesResponseDto addVisitedPlace(VisitedPlacesRequestDto requestDto) {
        Places place = placesRepository.findById(requestDto.getPlaceId())
                .orElseThrow(() -> new RuntimeException("장소를 찾을 수 없습니다. ID: " + requestDto.getPlaceId()));

        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다. ID: " + requestDto.getUserId()));

        Optional<VisitedPlaces> existingVisit = visitedPlacesRepository
                .findByUser_UserIdAndPlace_PlaceId(requestDto.getUserId(), requestDto.getPlaceId());

        VisitedPlaces visitedPlace;

        if (existingVisit.isPresent()) {
            visitedPlace = existingVisit.get();
            updateVisitedPlaceFields(visitedPlace, requestDto);
        } else {
            visitedPlace = new VisitedPlaces();
            visitedPlace.setUser(user);
            visitedPlace.setPlace(place);
            updateVisitedPlaceFields(visitedPlace, requestDto);
            visitedPlace.setCreatedAt(LocalDate.now());
        }

        VisitedPlaces saved = visitedPlacesRepository.save(visitedPlace);
        String userName = saved.getUser().getUserName(); // fromEntity 오류나서 수정 userName까지 넣어야 fromEntity 오류 안 남

        return VisitedPlacesResponseDto.fromEntity(saved, userName);
    }

    @Transactional
    public VisitedPlacesResponseDto updateVisitedPlace(Long visitId, VisitedPlacesRequestDto requestDto) {
        VisitedPlaces visitedPlace = visitedPlacesRepository.findById(visitId)
                .orElseThrow(() -> new RuntimeException("방문 이력을 찾을 수 없습니다. ID: " + visitId));

        updateVisitedPlaceFields(visitedPlace, requestDto);

        VisitedPlaces saved = visitedPlacesRepository.save(visitedPlace);
        String userName = saved.getUser().getUserName(); // userName 포함

        return VisitedPlacesResponseDto.fromEntity(saved, userName);
    }

    private void updateVisitedPlaceFields(VisitedPlaces visitedPlace, VisitedPlacesRequestDto requestDto) {
        if (requestDto.getVisitDate() != null) {
            visitedPlace.setVisitDate(LocalDate.parse(requestDto.getVisitDate())); // 문자열을 LocalDate로 변환
        }

        if (requestDto.getRating() != null) {
            visitedPlace.setRating(requestDto.getRating()); // Integer면 BigDecimal로 감쌀 필요 없음
        }

        if (requestDto.getNote() != null) {
            visitedPlace.setNote(requestDto.getNote());
        }
    }
    @Transactional
    public void deleteVisitedPlace(Long visitId) {
        VisitedPlaces visitedPlace = visitedPlacesRepository.findById(visitId)
                .orElseThrow(() -> new RuntimeException("방문 이력을 찾을 수 없습니다. ID: " + visitId));

        reportService.deleteAllByTargetTypeAndTargetId(TargetType.VISITEDPLACE, visitId);
        visitedPlacesRepository.delete(visitedPlace);
    }

    // 특정 사용자의 특정 장소 방문 이력 조회
    public Optional<VisitedPlaces> findByUserIdAndPlaceId(Long userId, Long placeId) {
        return visitedPlacesRepository.findByUser_UserIdAndPlace_PlaceId(userId, placeId);
    }

    // 모든 장소의 평균 별점 조회
    public Map<Long, Double> getAverageRatingsByPlaceId() {
        List<Object[]> results = visitedPlacesRepository.calculateAverageRatingByPlaceId();
        Map<Long, Double> averageRatings = new HashMap<>();
        
        for (Object[] result : results) {
            Long placeId = (Long) result[0];
            Double avgRating = (Double) result[1];
            averageRatings.put(placeId, avgRating);
        }
        
        return averageRatings;
    }

    // 특정 장소의 평균 별점 조회
    public Double getAverageRatingForPlace(Long placeId) {
        Double avgRating = visitedPlacesRepository.calculateAverageRatingForPlace(placeId);
        return avgRating != null ? avgRating : 0.0;
    }

    public VisitedPlacesResponseDto getVisitedPlaceById(Long visitId) {
        VisitedPlaces visitedPlaces = visitedPlacesRepository.findById(visitId)
                .orElseThrow(() -> new IllegalArgumentException("해당 방문 이력이 존재하지 않습니다: " + visitId));

        String userName = visitedPlaces.getUser() != null ? visitedPlaces.getUser().getUserName() : "익명";
        return VisitedPlacesResponseDto.fromEntity(visitedPlaces, userName);

    }

}

