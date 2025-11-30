package com.example.half_step_backend.service;

import com.example.half_step_backend.dto.AuthDto;
import com.example.half_step_backend.entity.User;
import com.example.half_step_backend.jwt.JwtTokenProvider;
import com.example.half_step_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    /**
     * 로그인
     */
    @Transactional
    public AuthDto.TokenResponse login(AuthDto.LoginRequest request) {
        // 1. Login ID/PW를 기반으로 Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getLoginId(), request.getPassword());

        // 2. 실제 검증 (비밀번호 체크)
        // authenticate() 실행 시 CustomUserDetailsService(만약 만들었다면)의 loadUserByUsername 실행됨
        // 여기서는 간단하게 Repository를 이용하거나 기본 Provider 사용
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        String accessToken = jwtTokenProvider.createToken(authentication);

        // 4. 사용자 ID 조회 (응답용)
        User user = userRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        return AuthDto.TokenResponse.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .userId(user.getUserId())
                .build();
    }

    /**
     * 회원가입
     */
    @Transactional
    public Long signup(AuthDto.SignupRequest request) {
        if (userRepository.existsByLoginId(request.getLoginId())) {
            throw new IllegalArgumentException("이미 가입된 아이디입니다.");
        }

        User user = User.builder()
                .loginId(request.getLoginId())
                .password(passwordEncoder.encode(request.getPassword())) // 비밀번호 암호화
                .name(request.getName())
                .birthDate(request.getBirthDate())
                .mbti(request.getMbti())
                .build();

        return userRepository.save(user).getUserId();
    }

    /**
     * 아이디 중복 체크
     */
    public boolean checkLoginId(String loginId) {
        return !userRepository.existsByLoginId(loginId);
    }
}