package com.example.half_step_backend.controller;

import com.example.half_step_backend.dto.PostDto;
import com.example.half_step_backend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 1. 게시글 목록 조회
    @GetMapping
    public ResponseEntity<PostDto.PostListResponse> getAllPosts() {
        PostDto.PostListResponse response = postService.getAllPosts();
        return ResponseEntity.ok(response);
    }

    // 2. 게시글 작성
    @PostMapping
    public ResponseEntity<PostDto.CreateResponse> createPost(@RequestBody PostDto.CreateRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentLoginId = authentication.getName();

        Long postId = postService.createPost(currentLoginId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PostDto.CreateResponse.builder()
                        .postId(postId)
                        .build());
    }

    // 3. 게시글 상세 조회
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto.PostDetailResponse> getPostDetail(@PathVariable Long postId) {
        PostDto.PostDetailResponse response = postService.getPostDetail(postId);
        return ResponseEntity.ok(response);
    }

    // 4. [New] 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<PostDto.DeleteResponse> deletePost(@PathVariable Long postId) {
        // 현재 로그인한 사용자 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentLoginId = authentication.getName();

        postService.deletePost(postId, currentLoginId);

        return ResponseEntity.ok(PostDto.DeleteResponse.builder()
                .message("게시글이 삭제되었습니다.")
                .build());
    }
}