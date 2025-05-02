package com.petplace.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "visited_places")
@Getter @Setter @NoArgsConstructor
public class VisitedPlaces {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visit_id")
    private Long visitId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Places place;

    @Column(name = "visit_date")
    private LocalDate visitDate;

    private Integer rating;

    private String note;

    @Column(name = "created_at")
    private LocalDate createdAt;

}
//바꾼것들
//VisitedPlaces
//place