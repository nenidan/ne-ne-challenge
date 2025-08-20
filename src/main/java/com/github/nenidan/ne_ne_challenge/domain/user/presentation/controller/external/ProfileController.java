package com.github.nenidan.ne_ne_challenge.domain.user.presentation.controller.external;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

@Tag(name="프로필 관리", description = "프로필 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProfileController {

    private final UserFacade userFacade;
    private final UserMapper userMapper;

    @Operation(summary = "본인 프로필 조회", description = "본인 프로필을 조회")
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

    @Operation(summary = "특정 사용자 프로필 조회", description = "특정 사용자 프로필을 조회")
    @GetMapping("/profiles/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getProfile(
            @Parameter(
                    description = "유저 ID",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {
        return ApiResponse.success(
                HttpStatus.OK,
                "프로필 조회가 완료되었습니다. id: " + id,
                userMapper.toResponse(
                        userFacade.getProfile(id)
                )
        );
    }

    @Operation(summary = "프로필 검색", description = "프로필 검색")
    @GetMapping("/profiles")
    public ResponseEntity<ApiResponse<CursorResponse<UserResponse, String>>> searchProfiles(
            @Parameter(description = "페이지 기준점")
            @RequestParam(defaultValue = "") String cursor,

            @Parameter(description = "페이지 크기")
            @RequestParam(defaultValue = "10") @Min(1) int size,

            @Parameter(description = "검색어")
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

    @Operation(summary = "프로필 검색 V2", description = "프로필 검색 (Elasticsearch 활용)")
    @GetMapping("/profiles/search")
    public ResponseEntity<ApiResponse<CursorResponse<UserResponse, String>>> searchProfilesV2(
            @Parameter(description = "페이지 기준점")
            @RequestParam(defaultValue = "") String cursor,

            @Parameter(description = "페이지 크기")
            @RequestParam(defaultValue = "10") @Min(1) int size,

            @Parameter(description = "검색어")
            @RequestParam(defaultValue = "") String keyword
    ) {

        CursorResponse<UserResult, String> res = userFacade.searchProfilesV2(cursor, size, keyword);
        List<UserResponse> content = res.getContent().stream()
                .map(userMapper::toResponse)
                .toList();

        return ApiResponse.success(
                HttpStatus.OK,
                "프로필 목록 조회가 완료되었습니다.",
                new CursorResponse<>(content, res.getNextCursor(), res.isHasNext())
        );
    }

    @Operation(summary = "본인 프로필 수정", description = "본인 프로필을 수정")
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
