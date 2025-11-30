package com.example.half_step_backend.controller;

import com.example.half_step_backend.dto.RankDto;
import com.example.half_step_backend.service.RankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ranks")
@RequiredArgsConstructor
public class RankController {

    private final RankService rankService;

    // 랭킹 리스트 조회
    // 예: GET /api/ranks?limit=20
    @GetMapping
    public ResponseEntity<RankDto.RankListResponse> getRanks(
            @RequestParam(defaultValue = "10") int limit
    ) {
        RankDto.RankListResponse response = rankService.getRankList(limit);
        return ResponseEntity.ok(response);
    }
}