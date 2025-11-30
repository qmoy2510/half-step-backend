package com.example.half_step_backend.service;

import com.example.half_step_backend.dto.PostDto;
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
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    /**
     * 게시글 작성
     */
    @Transactional
    public Long createPost(String loginId, PostDto.PostCreateRequest request) {
        User writer = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        writer.useSteps(request.getRewardSteps());

        Post post = Post.builder()
                .writer(writer)
                .title(request.getTitle())
                .content(request.getContent())
                .temperature(request.getTemperature())
                .rewardSteps(request.getRewardSteps())
                .build();

        return postRepository.save(post).getPostId();
    }

    /**
     * 게시글 전체 목록 조회
     */
    public PostDto.PostListResponse getAllPosts() {
        List<Post> posts = postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));

        List<PostDto.PostSummary> summaryList = posts.stream()
                .map(post -> PostDto.PostSummary.builder()
                        .postId(post.getPostId())
                        .title(post.getTitle())
                        .writerName(post.getWriter().getName())
                        .temperature(post.getTemperature())
                        .rewardSteps(post.getRewardSteps())
                        .viewCount(post.getViewCount())
                        .createdAt(post.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        return PostDto.PostListResponse.builder()
                .content(summaryList)
                .build();
    }

    /**
     * 게시글 상세 조회
     */
    public PostDto.PostDetailResponse getPostDetail(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        List<PostDto.PostCommentDto> commentDtos = commentRepository.findAllByPost_PostId(postId).stream()
                .map(comment -> PostDto.PostCommentDto.builder()
                        .commentId(comment.getCommentId())
                        .writerName(comment.getWriter().getName())
                        .writerLevel("아기새")
                        .content(comment.getContent())
                        .isSelected(comment.getIsSelected())
                        .build())
                .collect(Collectors.toList());

        PostDto.WriterInfo writerInfo = PostDto.WriterInfo.builder()
                .name(post.getWriter().getName())
                .level("알")
                .build();

        return PostDto.PostDetailResponse.builder()
                .postId(post.getPostId())
                .writerInfo(writerInfo)
                .title(post.getTitle())
                .content(post.getContent())
                .temperature(post.getTemperature())
                .rewardSteps(post.getRewardSteps())
                .comments(commentDtos)
                .build();
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public void deletePost(Long postId, String loginId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        if (!post.getWriter().getLoginId().equals(loginId)) {
            throw new IllegalArgumentException("작성자 본인만 삭제할 수 있습니다.");
        }

        commentRepository.deleteAllByPost_PostId(postId);
        postRepository.delete(post);
    }
}