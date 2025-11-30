package com.example.half_step_backend.repository;

import com.example.half_step_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 로그인 ID로 회원 조회 (로그인, 중복 검사 시 사용)
    Optional<User> findByLoginId(String loginId);

    // 로그인 ID 존재 여부 확인 (중복 가입 방지)
    boolean existsByLoginId(String loginId);
}