package com.petplace.dto;

import com.petplace.entity.Report;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponseDto {
    private Long reportId;
    private String targetType;
    private Long postId;
    private String reason;
    private Integer count;
    private Long userId;
    private String createdAt;

    public static ReportResponseDto fromEntity(Report report) {
        return new ReportResponseDto(
                report.getReportId(),
                report.getTargetType(),
                report.getPostId(),
                report.getReason(),
                report.getCount(),
                report.getUserId(),
                report.getCreatedAt().toString()
        );
    }
}