package com.example.half_step_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class RankDto {

    // 랭킹 목록 조회 Wrapper
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RankListResponse {
        private List<RankInfo> ranks;
    }

    // 개별 사용자 랭킹 정보
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RankInfo {
        private Integer rank;      // 등수 (1등, 2등...)
        private Long userId;       // 사용자 식별자
        private String name;       // 이름
        // private String levelName; // [Deleted] 명세서 반영: 레벨 이름 제거
        private Integer totalSteps;// 누적 발걸음
    }
}