package com.example.half_step_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class PostDto {

    // [Rename] CreateRequest -> PostCreateRequest
    @Getter
    @NoArgsConstructor
    public static class PostCreateRequest {
        private String title;
        private String content;
        private Integer temperature;
        private Integer rewardSteps;
    }

    // [Rename] CreateResponse -> PostCreateResponse
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostCreateResponse {
        private Long postId;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostListResponse {
        private List<PostSummary> content;
    }

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
        private List<PostCommentDto> comments;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WriterInfo {
        private String name;
        private String level;
    }

    // [Rename] CommentDto -> PostCommentDto (충돌 방지)
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostCommentDto {
        private Long commentId;
        private String writerName;
        private String writerLevel;
        private String content;
        private Boolean isSelected;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostDeleteResponse {
        private String message;
    }
}