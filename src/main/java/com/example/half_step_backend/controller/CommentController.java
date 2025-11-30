package com.example.half_step_backend.controller;

import com.example.half_step_backend.dto.CommentDto;
import com.example.half_step_backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 1. 답변 작성
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto.CommentCreateResponse> createComment(
            @PathVariable Long postId,
            @RequestBody CommentDto.CommentCreateRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentLoginId = authentication.getName();

        CommentDto.CommentCreateResponse response = commentService.createComment(postId, currentLoginId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 2. 답변 채택
    @PostMapping("/comments/{commentId}/select")
    public ResponseEntity<CommentDto.CommentSelectResponse> selectComment(@PathVariable Long commentId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentLoginId = authentication.getName();

        CommentDto.CommentSelectResponse response = commentService.selectComment(commentId, currentLoginId);

        return ResponseEntity.ok(response);
    }
}