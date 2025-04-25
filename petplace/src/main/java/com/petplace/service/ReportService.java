package com.petplace.service;

import com.petplace.dto.ReportRequestDto;
import com.petplace.entity.Report;
import com.petplace.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    public void saveReport(ReportRequestDto reportRequestDto){

        Report report = Report.builder()
                .targetId(reportRequestDto.getTargetId())
                .userId(reportRequestDto.getUserId())
                .reason(reportRequestDto.getReason())
                .targetType(reportRequestDto.getTargetType())
                .createdAt(LocalDateTime.now())
                .build();
        boolean alreadyExists = reportRepository.existsByTargetIdAndTargetTypeAndUserId(
                reportRequestDto.getTargetId(),
                reportRequestDto.getTargetType(),
                reportRequestDto.getUserId()

        );

        if (alreadyExists){
            throw  new IllegalComponentStateException("이미 신고하셨습니다.");
        }

        reportRepository.save(report);


    }

}