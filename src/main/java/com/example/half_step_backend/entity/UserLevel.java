package com.example.half_step_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_USER_LEVEL")
public class UserLevel {

    @Id
    @Column(name = "level_id")
    private Integer levelId; // 1, 2, 3...

    @Column(name = "level_name", nullable = false, length = 50)
    private String levelName; // 알, 아기새, 어른새...

    @Column(name = "required_steps", nullable = false, unique = true)
    private Integer requiredSteps; // 레벨업에 필요한 누적 발걸음 수

    @Column(name = "image_url")
    private String imageUrl; // 레벨별 이미지

    @Builder
    public UserLevel(Integer levelId, String levelName, Integer requiredSteps, String imageUrl) {
        this.levelId = levelId;
        this.levelName = levelName;
        this.requiredSteps = requiredSteps;
        this.imageUrl = imageUrl;
    }
}