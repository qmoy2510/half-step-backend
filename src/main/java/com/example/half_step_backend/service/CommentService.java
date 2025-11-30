package com.example.half_step_backend.service;

import com.example.half_step_backend.dto.CommentDto;
import com.example.half_step_backend.entity.Comment;
import com.example.half_step_backend.entity.Post;
import com.example.half_step_backend.entity.User;
import com.example.half_step_backend.repository.CommentRepository;
import com.example.half_step_backend.repository.PostRepository;
import com.example.half_step_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private static final int COMMENT_REWARD_STEPS = 3;

    /**
     * 답변 작성
     */
    @Transactional
    public CommentDto.CommentCreateResponse createComment(Long postId, String loginId, CommentDto.CommentCreateRequest request) {
        User writer = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        if (commentRepository.existsByPostAndWriter(post, writer)) {
            throw new IllegalArgumentException("한 게시글에 답변은 한 번만 작성할 수 있습니다.");
        }

        Comment comment = Comment.builder()
                .post(post)
                .writer(writer)
                .content(request.getContent())
                .build();
        commentRepository.save(comment);

        writer.addSteps(COMMENT_REWARD_STEPS);

        return CommentDto.CommentCreateResponse.builder()
                .commentId(comment.getCommentId())
                .earnedSteps(COMMENT_REWARD_STEPS)
                .build();
    }

    /**
     * 답변 채택
     */
    @Transactional
    public CommentDto.CommentSelectResponse selectComment(Long commentId, String loginId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("답변을 찾을 수 없습니다."));

        Post post = comment.getPost();

        if (!post.getWriter().getLoginId().equals(loginId)) {
            throw new IllegalArgumentException("게시글 작성자만 채택할 수 있습니다.");
        }

        if (post.getIsCompleted()) {
            throw new IllegalArgumentException("이미 채택이 완료된 게시글입니다.");
        }

        comment.select();
        post.complete();

        User selectedUser = comment.getWriter();
        selectedUser.addSteps(post.getRewardSteps());

        return CommentDto.CommentSelectResponse.builder()
                .success(true)
                .message("채택이 완료되었습니다. 대상자에게 " + post.getRewardSteps() + "걸음이 지급되었습니다.")
                .build();
    }
}