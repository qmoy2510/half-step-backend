package com.example.half_step_backend.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert // null인 필드 제외하고 insert (Default 값 적용 위해)
@Table(name = "TB_USER")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Oracle 12c+ Identity Column 사용
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "login_id", nullable = false, unique = true, length = 50)
    private String loginId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "mbti", nullable = false, length = 4)
    private String mbti;

    @Column(name = "current_steps")
    @ColumnDefault("0")
    private Integer currentSteps;

    @Column(name = "total_steps")
    @ColumnDefault("0")
    private Integer totalSteps;

    @Column(name = "is_filter_on")
    @ColumnDefault("true") // Oracle에서는 1/0 또는 'Y'/'N' 변환 필요할 수 있으나 JPA가 자동 처리
    private Boolean isFilterOn;

    @Column(name = "is_deleted")
    @ColumnDefault("false")
    private Boolean isDeleted;

    @Builder
    public User(String loginId, String password, String name, LocalDate birthDate, String mbti) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.birthDate = birthDate;
        this.mbti = mbti;
    }

    // 비즈니스 로직 메소드 (Setter 대신 사용)
    public void addSteps(int amount) {
        this.currentSteps += amount;
        this.totalSteps += amount;
    }

    public void useSteps(int amount) {
        if (this.currentSteps < amount) {
            throw new IllegalArgumentException("보유 발걸음이 부족합니다.");
        }
        this.currentSteps -= amount;
    }

    public void updateFilter(boolean isFilterOn) {
        this.isFilterOn = isFilterOn;
    }
}