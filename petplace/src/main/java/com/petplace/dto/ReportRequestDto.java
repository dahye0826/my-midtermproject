package com.petplace.dto;

import com.petplace.constant.TargetType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportRequestDto {


    private Long targetId;      // 신고 대상 ID
    private Long userId;        // 신고한 사용자 ID
    private String reason;      // 신고 사유
    private TargetType targetType;
}
