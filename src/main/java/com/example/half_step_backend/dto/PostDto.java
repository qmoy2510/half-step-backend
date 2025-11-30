package com.example.half_step_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class PostDto {

    // 1. 게시글 작성 Request
    @Getter
    @NoArgsConstructor
    public static class CreateRequest {
        private String title;
        private String content;
        private Integer temperature;
        private Integer rewardSteps;
    }

    // 2. 게시글 작성 Response
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateResponse {
        private Long postId;
    }

    // 3. 게시글 목록 조회 Response
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostListResponse {
        private List<PostSummary> content;
    }

    // 목록 내부의 요약 정보
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostSummary {
        private Long postId;
        private String title;
        private String writerName;
        private Integer temperature;
        private Integer rewardSteps;
        private Integer viewCount;
        private LocalDateTime createdAt;
    }

    // 4. 게시글 상세 조회 Response
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostDetailResponse {
        private Long postId;
        private WriterInfo writerInfo;
        private String title;
        private String content;
        private Integer temperature;
        private Integer rewardSteps;
        private List<CommentDto> comments;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WriterInfo {
        private String name;
        private String level;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentDto {
        private Long commentId;
        private String writerName;
        private String writerLevel;
        private String content;
        private Boolean isSelected;
    }

    // [New] 게시글 삭제 Response
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeleteResponse {
        private String message;
    }
}