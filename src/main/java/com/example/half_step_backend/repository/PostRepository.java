package com.example.half_step_backend.repository;

import com.example.half_step_backend.entity.Post;
import com.example.half_step_backend.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByWriter_UserId(Long writerId, Sort sort);

    long countByWriter(User writer);

    // [New] 특정 사용자가 쓴 글 모두 조회 (삭제 전 댓글 정리를 위해 필요)
    List<Post> findAllByWriter(User writer);

    // [New] 특정 사용자가 쓴 글 모두 삭제
    void deleteAllByWriter(User writer);
}