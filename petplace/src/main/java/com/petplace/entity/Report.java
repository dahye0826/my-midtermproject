package com.petplace.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "report")
@Getter @Setter @NoArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id", length = 10)
    private Long reportId;

    @Column(name = "target_type", length = 20)
    private String targetType;

    @Column(name = "post_id", length = 10)
    private Long postId;

    @Column(length = 255)
    private String reason;

    @Column(length = 5)
    private Integer count;

    @Column(name = "user_id", length = 20)
    private Long userId;

    @Column(name = "created_at")
    private LocalDate createdAt;
}