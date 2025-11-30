package com.example.half_step_backend.service;

import com.example.half_step_backend.dto.RankDto;
import com.example.half_step_backend.entity.User;
import com.example.half_step_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RankService {

    private final UserRepository userRepository;
    // UserLevelRepository 제거 (레벨 계산 불필요)

    /**
     * 랭킹 리스트 조회
     * @param limit 조회할 상위 랭커 수 (기본 10명)
     */
    public RankDto.RankListResponse getRankList(int limit) {
        // 1. 발걸음 순으로 상위 유저 조회
        List<User> topUsers = userRepository.findAllByOrderByTotalStepsDesc(PageRequest.of(0, limit));

        List<RankDto.RankInfo> rankInfos = new ArrayList<>();

        // 2. 랭킹 정보 매핑 (레벨 이름 계산 로직 삭제)
        for (int i = 0; i < topUsers.size(); i++) {
            User user = topUsers.get(i);

            rankInfos.add(RankDto.RankInfo.builder()
                    .rank(i + 1)
                    .userId(user.getUserId())
                    .name(user.getName())
                    .totalSteps(user.getTotalSteps())
                    .build());
        }

        return RankDto.RankListResponse.builder()
                .ranks(rankInfos)
                .build();
    }
}