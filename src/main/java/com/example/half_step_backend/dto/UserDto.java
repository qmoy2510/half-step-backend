package com.example.half_step_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class UserDto {

    // 1. 마이페이지 정보 조회 Response
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MyPageResponse {
        private String name;
        private String mbti;
        private Integer currentSteps;
        private Integer totalSteps;

        // [Delete] levelId, levelName 제거됨

        private Long postCount;
        private Long commentCount;
        private Boolean isFilterOn;
    }

    // 2. 내 목록 조회용 Wrapper Response
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MyListResponse<T> {
        private List<T> data;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MyPostDto {
        private Long id;
        private String title;
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MyCommentDto {
        private Long id;
        private String content;
        private LocalDateTime createdAt;
    }

    // 3. 필터 설정 Request
    @Getter
    @NoArgsConstructor
    public static class FilterUpdateRequest {
        private Boolean isFilterOn;
    }

    // 4. 필터 설정 Response
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FilterUpdateResponse {
        private Boolean updatedFilterStatus;
    }

    // 5. 회원 탈퇴 Response
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WithdrawResponse {
        private String message;
    }
}