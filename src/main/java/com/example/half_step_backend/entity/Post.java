package com.example.half_step_backend.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Table(name = "TB_POST")
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", nullable = false)
    private User writer;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "CLOB") // Oracle Text 타입
    private String content;

    @Column(name = "temperature", nullable = false)
    private Integer temperature;

    @Column(name = "reward_steps", nullable = false)
    private Integer rewardSteps;

    @Column(name = "is_completed")
    @ColumnDefault("false")
    private Boolean isCompleted;

    @Column(name = "view_count")
    @ColumnDefault("0")
    private Integer viewCount;

    @Builder
    public Post(User writer, String title, String content, Integer temperature, Integer rewardSteps) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.temperature = temperature;
        this.rewardSteps = rewardSteps;
    }

    public void complete() {
        this.isCompleted = true;
    }
}