package com.github.nenidan.ne_ne_challenge.domain.user.controller;

import com.github.nenidan.ne_ne_challenge.domain.user.dto.request.UpdateProfileRequest;
import com.github.nenidan.ne_ne_challenge.domain.user.dto.response.UserResponse;
import com.github.nenidan.ne_ne_challenge.domain.user.service.UserService;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;
import com.github.nenidan.ne_ne_challenge.global.security.auth.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProfileController {

    private final UserService userService;

    @GetMapping("/profiles/me")
    public ResponseEntity<ApiResponse<UserResponse>> getMyProfile(@AuthenticationPrincipal Auth auth) {
        return ApiResponse.success(
                HttpStatus.OK,
                "프로필 조회가 완료되었습니다.",
                userService.getProfile(auth.getId())
        );
    }

    @GetMapping("/profiles/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getMyProfile(@PathVariable Long id) {
        return ApiResponse.success(
                HttpStatus.OK,
                "프로필 조회가 완료되었습니다. id: " + id,
                userService.getProfile(id)
        );
    }

    @GetMapping("/profiles")
    public ResponseEntity<ApiResponse<CursorResponse<UserResponse, String>>> searchProfiles(
            @RequestParam(defaultValue = "") String cursor,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String keyword
    ) {
        return ApiResponse.success(
                HttpStatus.OK,
                "프로필 목록 조회가 완료되었습니다.",
                userService.searchProfiles(cursor, size, keyword)
        );
    }

    @PatchMapping("/profiles/me")
    public ResponseEntity<ApiResponse<UserResponse>> updateMyProfile(
            @AuthenticationPrincipal Auth auth,
            @RequestBody UpdateProfileRequest updateProfileRequest
    ) {
        return ApiResponse.success(
                HttpStatus.OK,
                "프로필 수정이 완료되었습니다.",
                userService.updateProfile(auth.getId(), updateProfileRequest)
        );
    }
}
