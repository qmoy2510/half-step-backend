package com.example.half_step_backend.repository;

import com.example.half_step_backend.entity.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 작성자(User)로 게시글 조회 (마이페이지) - 최신순 정렬
    List<Post> findAllByWriter_UserId(Long writerId, Sort sort);

    // 전체 조회 시 정렬은 Service 계층에서 Sort 객체를 넘겨서 처리 (findAll(Sort sort)는 기본 내장)
}