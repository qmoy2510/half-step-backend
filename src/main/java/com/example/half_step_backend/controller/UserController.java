package com.example.half_step_backend.controller;

import com.example.half_step_backend.dto.UserDto;
import com.example.half_step_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/me")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDto.MyPageResponse> getMyPageInfo() {
        String currentLoginId = getCurrentLoginId();
        return ResponseEntity.ok(userService.getMyPageInfo(currentLoginId));
    }

    // [Update] 반환 타입 변경 (PostSummary -> MyPostDto)
    @GetMapping("/posts")
    public ResponseEntity<UserDto.MyListResponse<UserDto.MyPostDto>> getMyPosts() {
        String currentLoginId = getCurrentLoginId();
        return ResponseEntity.ok(userService.getMyPosts(currentLoginId));
    }

    // [Update] 반환 타입 변경
    @GetMapping("/comments")
    public ResponseEntity<UserDto.MyListResponse<UserDto.MyCommentDto>> getMyComments() {
        String currentLoginId = getCurrentLoginId();
        return ResponseEntity.ok(userService.getMyComments(currentLoginId));
    }

    @PatchMapping("/settings/filter")
    public ResponseEntity<UserDto.FilterUpdateResponse> updateFilter(@RequestBody UserDto.FilterUpdateRequest request) {
        String currentLoginId = getCurrentLoginId();
        boolean updatedStatus = userService.updateFilter(currentLoginId, request.getIsFilterOn());

        return ResponseEntity.ok(UserDto.FilterUpdateResponse.builder()
                .updatedFilterStatus(updatedStatus)
                .build());
    }

    @DeleteMapping
    public ResponseEntity<UserDto.WithdrawResponse> withdraw() {
        String currentLoginId = getCurrentLoginId();
        userService.withdraw(currentLoginId);

        return ResponseEntity.ok(UserDto.WithdrawResponse.builder()
                .message("회원 탈퇴가 완료되었습니다.")
                .build());
    }

    private String getCurrentLoginId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}