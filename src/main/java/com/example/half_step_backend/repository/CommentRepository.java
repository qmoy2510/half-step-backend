package com.example.half_step_backend.repository;

import com.example.half_step_backend.entity.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 특정 게시글에 달린 댓글 목록 조회
    List<Comment> findAllByPost_PostId(Long postId);

    // 작성자(User)가 쓴 댓글 조회 (마이페이지)
    List<Comment> findAllByWriter_UserId(Long writerId, Sort sort);

    // [New] 특정 게시글에 달린 댓글 전체 삭제 (게시글 삭제 시 사용)
    void deleteAllByPost_PostId(Long postId);
}