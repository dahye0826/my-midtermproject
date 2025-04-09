package com.petplace.service;

import com.petplace.dto.ReportResponseDto;
import com.petplace.entity.Report;
import com.petplace.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    public Map<String, Object> getReports(String targetType, Long postId, Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("reportId").descending());

        Page<Report> reportsPage = reportRepository.findReportsWithFilters(targetType, postId, userId, pageable);

        Page<ReportResponseDto> reportsDto = reportsPage.map(ReportResponseDto::fromEntity);

        Map<String, Object> response = new HashMap<>();
        response.put("reports", reportsDto.getContent());
        response.put("currentPage", reportsPage.getNumber() + 1);
        response.put("totalItems", reportsPage.getTotalElements());
        response.put("totalPages", reportsPage.getTotalPages());

        return response;
    }

    public ReportResponseDto getReportById(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + reportId));
        return ReportResponseDto.fromEntity(report);
    }
}