package com.example.half_step_backend.repository;

import com.example.half_step_backend.entity.Comment;
import com.example.half_step_backend.entity.Post;
import com.example.half_step_backend.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost_PostId(Long postId);
    List<Comment> findAllByWriter_UserId(Long writerId, Sort sort);

    void deleteAllByPost_PostId(Long postId);

    boolean existsByPostAndWriter(Post post, User writer);

    long countByWriter(User writer);

    // [New] 특정 사용자가 쓴 댓글 모두 삭제 (회원 탈퇴용)
    void deleteAllByWriter(User writer);
}