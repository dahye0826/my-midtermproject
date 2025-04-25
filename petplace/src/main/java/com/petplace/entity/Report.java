package com.petplace.entity;

import com.petplace.constant.TargetType;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.*;

@Builder
@AllArgsConstructor
@Entity
@Table(name = "report")
@Getter @Setter @NoArgsConstructor
public class Report{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportId;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", length = 20)
    private TargetType targetType;

    @Column(name = "target_id")
    private Long targetId;

    @Column(length = 50)
    private String reason;

    @Column
    private Integer count;

    @Column(name = "user_id", length = 20)
    private Long userId;
    @Column(name = "created_at")
    private LocalDateTime createdAt;


}