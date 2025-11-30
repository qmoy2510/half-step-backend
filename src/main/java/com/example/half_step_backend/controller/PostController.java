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

    @GetMapping
    public ResponseEntity<PostDto.PostListResponse> getAllPosts() {
        PostDto.PostListResponse response = postService.getAllPosts();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<PostDto.PostCreateResponse> createPost(@RequestBody PostDto.PostCreateRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentLoginId = authentication.getName();

        Long postId = postService.createPost(currentLoginId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PostDto.PostCreateResponse.builder()
                        .postId(postId)
                        .build());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto.PostDetailResponse> getPostDetail(@PathVariable Long postId) {
        PostDto.PostDetailResponse response = postService.getPostDetail(postId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<PostDto.PostDeleteResponse> deletePost(@PathVariable Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentLoginId = authentication.getName();

        postService.deletePost(postId, currentLoginId);

        return ResponseEntity.ok(PostDto.PostDeleteResponse.builder()
                .message("게시글이 삭제되었습니다.")
                .build());
    }
}