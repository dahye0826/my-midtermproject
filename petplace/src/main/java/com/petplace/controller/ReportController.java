package com.petplace.controller;

import com.petplace.dto.ReportRequestDto;
import com.petplace.dto.ReportResponseDto;
import com.petplace.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}