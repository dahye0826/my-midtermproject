package com.petplace.service;

import com.petplace.constant.TargetType;
import com.petplace.dto.ReportRequestDto;
import com.petplace.entity.Report;
import com.petplace.repository.ReportRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

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
            throw new IllegalStateException("이미 신고하셨습니다.");
        }

        reportRepository.save(report);


    }

    public long countAllReports() {
        return reportRepository.count();
    }


    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @Transactional
    public void deleteAllByTargetTypeAndTargetId(TargetType targetType, Long targetId) {
        reportRepository.deleteAllByTargetTypeAndTargetId(targetType, targetId);
    }

    public void deleteReportById(Long reportId) {
        reportRepository.deleteById(reportId);
    }



}