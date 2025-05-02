package com.petplace.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 장소 엔티티
 * 반려동물 동반 가능한 장소 정보를 저장하는 클래스
 */
@Entity
@Table(name = "places")
@Getter @Setter @NoArgsConstructor
public class Places {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long placeId;

    @Column(name = "place_image")
    private String placeImage; // 장소 이미지 경로

    @Column(name = "place_name")
    private String placeName; // 장소명

    @Column(name = "industry_main")
    private String industryMain; // 대분류 카테고리

    @Column(name = "industry_mid")
    private String industryMid; // 중분류 카테고리

    @Column(name = "industry_sub")
    private String industrySub; // 소분류 카테고리

    // 주소 관련 필드
    private String city; // 도시
    private String district; // 지역구
    private String town; // 동/읍/면
    private String village; // 리/마을

    @Column(name = "lot_number")
    private String lotNumber; // 지번

    @Column(name = "street_name")
    private String streetName; // 도로명

    @Column(name = "building_number")
    private String buildingNumber; // 건물번호

    // 위치 좌표
    private Double latitude; // 위도
    private Double longitude; // 경도

    @Column(name = "postal_code")
    private String postalCode; // 우편번호

    @Column(name = "road_address")
    private String roadAddress; // 도로명 주소

    @Column(name = "land_address")
    private String landAddress; // 지번 주소

    @Column(name = "place_phone")
    private String placePhone; // 연락처

    private String homepage; // 홈페이지

    @Column(name = "closed_day")
    private String closedDay; // 휴무일

    @Column(name = "opening_hours")
    private String openingHours; // 영업시간

    @Column(name = "parking_available")
    private String parkingAvailable; // 주차 가능 여부

    @Column(name = "admission_fee")
    private String admissionFee; // 입장료

    @Column(name = "pet_restrictions")
    private String petRestrictions; // 반려동물 제한사항

    private String indoor; // 실내 가능 여부
    private String outdoor; // 실외 가능 여부
    private String description; // 장소 설명

    @Column(name = "pet_extra_charge")
    private String petExtraCharge; // 반려동물 추가 요금

    @Column(name = "pet_size")
    private String petSize; // 허용되는 반려동물 크기

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated; // 최종 업데이트 일시

    // 연관 관계 매핑
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Post> posts = new ArrayList<>(); // 장소 관련 게시물 목록

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<VisitedPlaces> visitedPlaces = new ArrayList<>(); // 방문 기록 목록

    /**
     * 전체 주소를 조합하여 반환하는 메서드
     * @return 전체 주소 문자열
     */
    @Transient
    public String getFullAddress() {
        StringBuilder address = new StringBuilder();
        if (city != null && !city.isEmpty()) address.append(city).append(" ");
        if (district != null && !district.isEmpty()) address.append(district).append(" ");
        if (town != null && !town.isEmpty()) address.append(town).append(" ");
        if (village != null && !village.isEmpty()) address.append(village).append(" ");
        if (lotNumber != null && !lotNumber.isEmpty()) address.append(lotNumber);
        return address.toString().trim();
    }
}