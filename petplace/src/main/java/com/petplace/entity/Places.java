package com.petplace.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "places")
@Getter @Setter @NoArgsConstructor
public class Places {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long placeId;

    @Column(name = "place_image")
    private String placeImage;

    @Column(name = "place_name")
    private String placeName;

    @Column(name = "industry_main")
    private String industryMain;

    @Column(name = "industry_mid")
    private String industryMid;

    @Column(name = "industry_sub")
    private String industrySub;

    private String city;
    private String district;
    private String town;
    private String village;

    @Column(name = "lot_number")
    private String lotNumber;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "building_number")
    private String buildingNumber;

    private Double latitude;
    private Double longitude;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "road_address")
    private String roadAddress;

    @Column(name = "land_address")
    private String landAddress;

    @Column(name = "place_phone")
    private String placePhone;

    private String homepage;

    @Column(name = "closed_day")
    private String closedDay;

    @Column(name = "opening_hours")
    private String openingHours;

    @Column(name = "parking_available")
    private String parkingAvailable;

    @Column(name = "admission_fee")
    private String admissionFee;

    @Column(name = "pet_restrictions")
    private String petRestrictions;

    private String indoor;
    private String outdoor;
    private String description;

    @Column(name = "pet_extra_charge")
    private String petExtraCharge;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Post> posts = new ArrayList<>();
}