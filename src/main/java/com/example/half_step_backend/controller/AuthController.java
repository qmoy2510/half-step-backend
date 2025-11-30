package com.example.half_step_backend.controller;

import com.example.half_step_backend.dto.AuthDto;
import com.example.half_step_backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 1. 로그인
    @PostMapping("/login")
    public ResponseEntity<AuthDto.TokenResponse> login(@RequestBody AuthDto.LoginRequest request) {
        AuthDto.TokenResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    // 2. 회원가입
    @PostMapping("/signup")
    public ResponseEntity<AuthDto.SignupResponse> signup(@RequestBody AuthDto.SignupRequest request) {
        Long userId = authService.signup(request);

        // JSON 객체 { "userId": 1 } 형태로 변환하여 반환
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AuthDto.SignupResponse.builder()
                        .userId(userId)
                        .build());
    }

    // 3. 아이디 중복 확인 (수정됨)
    @GetMapping("/check-id")
    public ResponseEntity<AuthDto.CheckIdResponse> checkLoginId(@RequestParam String loginId) {
        boolean isAvailable = authService.checkLoginId(loginId);

        // 명세서에 맞춰 { "isAvailable": true/false } 형태의 JSON 객체 반환
        return ResponseEntity.ok(AuthDto.CheckIdResponse.builder()
                .isAvailable(isAvailable)
                .build());
    }
}