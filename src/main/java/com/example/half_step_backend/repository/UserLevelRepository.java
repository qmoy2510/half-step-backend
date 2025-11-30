package com.example.half_step_backend.repository;

import com.example.half_step_backend.entity.UserLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLevelRepository extends JpaRepository<UserLevel, Integer> {

    // [New] 내 누적 발걸음(totalSteps)보다 작거나 같은 레벨 중
    // 가장 높은 단계(requiredSteps가 가장 큰 것) 1개를 조회
    // 예: 내 발걸음 70 -> 50(아기새) 조회됨
    Optional<UserLevel> findTopByRequiredStepsLessThanEqualOrderByRequiredStepsDesc(int totalSteps);
}