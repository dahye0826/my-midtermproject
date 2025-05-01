package com.petplace.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "favorites")
@Getter @Setter @NoArgsConstructor
public class Favorites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id", length = 10)
    private Long favoriteId;

    @Column(name = "user_id", length = 20)
    private Long userId;

    @Column(name = "place_id", length = 10)
    private Long placeId;

    @Column(name = "added_date")
    private LocalDate addedDate;
}