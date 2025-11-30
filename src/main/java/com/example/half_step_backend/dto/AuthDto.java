package com.example.half_step_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class AuthDto {

    @Getter
    @NoArgsConstructor
    public static class LoginRequest {
        private String loginId;
        private String password;
    }

    @Getter
    @NoArgsConstructor
    public static class SignupRequest {
        private String loginId;
        private String password;
        private String name;
        private LocalDate birthDate;
        private String mbti;
    }

    @Getter
    @Builder
    public static class TokenResponse {
        private String grantType;
        private String accessToken;
        private Long userId;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SignupResponse {
        private Long userId;
    }

    // [New] 아이디 중복 확인 응답 DTO 추가
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CheckIdResponse {
        private boolean isAvailable;
    }
}