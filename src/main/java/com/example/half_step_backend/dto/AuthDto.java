package com.example.half_step_backend.dto;

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
        private Long userId; // 프론트 편의를 위해 추가
    }
}