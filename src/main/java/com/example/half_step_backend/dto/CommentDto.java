package com.example.half_step_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentDto {

    // [Rename] CreateRequest -> CommentCreateRequest
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentCreateRequest {
        private String content;
    }

    // [Rename] CreateResponse -> CommentCreateResponse
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentCreateResponse {
        private Long commentId;
        private Integer earnedSteps;
    }

    // [Rename] SelectResponse -> CommentSelectResponse
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentSelectResponse {
        private Boolean success;
        private String message;
    }
}