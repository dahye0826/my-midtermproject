package com.petplace.controller;

import com.petplace.constant.TargetType;
import com.petplace.dto.ReportRequestDto;
import com.petplace.dto.ReportResponseDto;
import com.petplace.entity.Report;
import com.petplace.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;


    @PostMapping
    public ResponseEntity<String> report(@RequestBody ReportRequestDto requestDto){
        reportService.saveReport(requestDto);
        return ResponseEntity.ok("신고가 접수되었습니다.");
    }

    @GetMapping
    public ResponseEntity<?> getAllReports(){
        return ResponseEntity.ok(reportService.getAllReports());
    }
    @GetMapping("/count")
    public ResponseEntity<Long> getReportCount() {
        return ResponseEntity.ok(reportService.countAllReports());
    }


    @DeleteMapping("{reportId}")
    public ResponseEntity<String> deleteReport(@PathVariable Long reportId) {
        reportService.deleteReportById(reportId);
        return ResponseEntity.ok("신고가 삭제되었습니다.");
    }

}