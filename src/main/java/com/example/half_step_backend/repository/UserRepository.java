package com.example.half_step_backend.repository;

import com.example.half_step_backend.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginId(String loginId);
    boolean existsByLoginId(String loginId);

    // [New] 누적 발걸음 내림차순 조회 (랭킹용)
    // Pageable을 파라미터로 넘겨서 limit 개수만큼만 가져옴
    List<User> findAllByOrderByTotalStepsDesc(Pageable pageable);
}