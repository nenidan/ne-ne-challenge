package com.github.nenidan.ne_ne_challenge.domain.user.presentation.controller.external;

import com.github.nenidan.ne_ne_challenge.domain.user.application.UserFacade;
import com.github.nenidan.ne_ne_challenge.domain.user.application.dto.UserResult;
import com.github.nenidan.ne_ne_challenge.domain.user.presentation.dto.request.UpdateProfileRequest;
import com.github.nenidan.ne_ne_challenge.domain.user.presentation.dto.response.UserResponse;
import com.github.nenidan.ne_ne_challenge.domain.user.presentation.mapper.UserMapper;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;
import com.github.nenidan.ne_ne_challenge.global.security.auth.Auth;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProfileController {

    private final UserFacade userFacade;
    private final UserMapper userMapper;

    @GetMapping("/profiles/me")
    public ResponseEntity<ApiResponse<UserResponse>> getMyProfile(@AuthenticationPrincipal Auth auth) {
        return ApiResponse.success(
                HttpStatus.OK,
                "프로필 조회가 완료되었습니다.",
                userMapper.toResponse(
                        userFacade.getProfile(auth.getId())
                )
        );
    }

    @GetMapping("/profiles/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getProfile(@PathVariable Long id) {
        return ApiResponse.success(
                HttpStatus.OK,
                "프로필 조회가 완료되었습니다. id: " + id,
                userMapper.toResponse(
                        userFacade.getProfile(id)
                )
        );
    }

    @GetMapping("/profiles")
    public ResponseEntity<ApiResponse<CursorResponse<UserResponse, String>>> searchProfiles(
            @RequestParam(defaultValue = "") String cursor,
            @RequestParam(defaultValue = "10") @Min(1) int size,
            @RequestParam(defaultValue = "") String keyword
    ) {

        CursorResponse<UserResult, String> res = userFacade.searchProfiles(cursor, size, keyword);
        List<UserResponse> content = res.getContent().stream()
                .map(userMapper::toResponse)
                .toList();

        return ApiResponse.success(
                HttpStatus.OK,
                "프로필 목록 조회가 완료되었습니다.",
                new CursorResponse<>(content, res.getNextCursor(), res.isHasNext())
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
                userMapper.toResponse(
                        userFacade.updateProfile(auth.getId(), userMapper.toDto(updateProfileRequest))
                )

        );
    }
}
