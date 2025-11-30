package com.example.half_step_backend.repository;

import com.example.half_step_backend.entity.UserLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLevelRepository extends JpaRepository<UserLevel, Integer> {
    // 특정 발걸음 수 조건에 맞는 레벨을 찾는 로직이 필요할 경우 추가 예정
    // 예: findTopByRequiredStepsLessThanEqualOrderByRequiredStepsDesc(int totalSteps);
}