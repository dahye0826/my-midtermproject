package com.petplace.controller;

import com.petplace.dto.ReportResponseDto;
import com.petplace.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getReports(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String targetType,
            @RequestParam(required = false) Long postId,
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "10") int size) {

        Map<String, Object> response = reportService.getReports(targetType, postId, userId, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<ReportResponseDto> getReportById(@PathVariable Long reportId) {
        ReportResponseDto report = reportService.getReportById(reportId);
        return ResponseEntity.ok(report);
    }
}