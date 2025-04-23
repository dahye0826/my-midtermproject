package com.petplace.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "pet_size")
    private String petSize;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<VisitedPlaces> visitedPlaces = new ArrayList<>();

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