package com.example.half_step_backend.service;

import com.example.half_step_backend.dto.UserDto;
import com.example.half_step_backend.entity.Post;
import com.example.half_step_backend.entity.User;
import com.example.half_step_backend.repository.CommentRepository;
import com.example.half_step_backend.repository.PostRepository;
import com.example.half_step_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public UserDto.MyPageResponse getMyPageInfo(String loginId) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        long postCount = postRepository.countByWriter(user);
        long commentCount = commentRepository.countByWriter(user);

        return UserDto.MyPageResponse.builder()
                .name(user.getName())
                .mbti(user.getMbti())
                .currentSteps(user.getCurrentSteps())
                .totalSteps(user.getTotalSteps())
                .postCount(postCount)
                .commentCount(commentCount)
                .isFilterOn(user.getIsFilterOn())
                .build();
    }

    public UserDto.MyListResponse<UserDto.MyPostDto> getMyPosts(String loginId) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        List<Post> myPosts = postRepository.findAllByWriter_UserId(user.getUserId(), Sort.by(Sort.Direction.DESC, "createdAt"));

        List<UserDto.MyPostDto> dtoList = myPosts.stream()
                .map(post -> UserDto.MyPostDto.builder()
                        .id(post.getPostId())
                        .title(post.getTitle())
                        .createdAt(post.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        return UserDto.MyListResponse.<UserDto.MyPostDto>builder()
                .data(dtoList)
                .build();
    }

    public UserDto.MyListResponse<UserDto.MyCommentDto> getMyComments(String loginId) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        List<UserDto.MyCommentDto> dtoList = commentRepository.findAllByWriter_UserId(user.getUserId(), Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream()
                .map(comment -> UserDto.MyCommentDto.builder()
                        .id(comment.getCommentId())
                        .content(comment.getContent())
                        .createdAt(comment.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        return UserDto.MyListResponse.<UserDto.MyCommentDto>builder()
                .data(dtoList)
                .build();
    }

    @Transactional
    public boolean updateFilter(String loginId, boolean isFilterOn) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        user.updateFilter(isFilterOn);
        return user.getIsFilterOn();
    }

    /**
     * 회원 탈퇴 (Hard Delete)
     * 외래키 제약조건 해결을 위해 연관 데이터(댓글, 게시글)를 먼저 삭제합니다.
     */
    @Transactional
    public void withdraw(String loginId) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 1. 내가 작성한 댓글 모두 삭제
        commentRepository.deleteAllByWriter(user);

        // 2. 내가 작성한 게시글 삭제
        // (주의: 게시글을 지우기 전에, 그 게시글에 달린 다른 사람의 댓글부터 지워야 함)
        List<Post> myPosts = postRepository.findAllByWriter(user);
        for (Post post : myPosts) {
            commentRepository.deleteAllByPost_PostId(post.getPostId());
        }

        // 댓글 정리가 끝났으므로 게시글 일괄 삭제
        postRepository.deleteAllByWriter(user);

        // 3. 마지막으로 회원 정보 삭제
        userRepository.delete(user);
    }
}