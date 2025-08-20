package com.github.nenidan.ne_ne_challenge.domain.user.presentation.controller.external;

import com.github.nenidan.ne_ne_challenge.global.aop.annotation.AuditIgnore;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.user.application.UserFacade;
import com.github.nenidan.ne_ne_challenge.domain.user.application.client.oauth.type.Provider;
import com.github.nenidan.ne_ne_challenge.domain.user.application.dto.UserWithTokenResult;
import com.github.nenidan.ne_ne_challenge.domain.user.presentation.dto.request.JoinRequest;
import com.github.nenidan.ne_ne_challenge.domain.user.presentation.dto.request.LoginRequest;
import com.github.nenidan.ne_ne_challenge.domain.user.presentation.dto.request.OAuthLoginRequest;
import com.github.nenidan.ne_ne_challenge.domain.user.presentation.dto.request.UpdatePasswordRequest;
import com.github.nenidan.ne_ne_challenge.domain.user.presentation.dto.request.VerifyPasswordRequest;
import com.github.nenidan.ne_ne_challenge.domain.user.presentation.dto.response.UserResponse;
import com.github.nenidan.ne_ne_challenge.domain.user.presentation.mapper.UserMapper;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import com.github.nenidan.ne_ne_challenge.global.security.auth.Auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name="사용자 관리", description = "사용자 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountController {

    private final UserFacade userFacade;
    private final UserMapper userMapper;

    @Operation(summary = "회원가입", description = "신규 사용자를 등록")
    @PostMapping("/accounts")
    public ResponseEntity<ApiResponse<UserResponse>> join(@RequestBody @Valid JoinRequest joinRequest) {

        UserWithTokenResult user = userFacade.join(userMapper.toDto(joinRequest));

        return ApiResponse.success(
                HttpStatus.CREATED,
                "회원가입이 완료되었습니다.",
                userMapper.toResponse(user.getUserResult()),
                user.getHeaders()
        );
    }

    @Operation(summary = "로그인", description = "사용자 로그인 처리")
    @PostMapping("/accounts/login")
    public ResponseEntity<ApiResponse<UserResponse>> login(@RequestBody @Valid LoginRequest loginRequest) {

        UserWithTokenResult user = userFacade.login(userMapper.toDto(loginRequest));

        return ApiResponse.success(
                HttpStatus.OK,
                "로그인이 완료되었습니다.",
                userMapper.toResponse(user.getUserResult()),
                user.getHeaders()
        );
    }

    @Operation(summary = "소셜 로그인", description = "카카오/네이버 소셜 로그인 처리")
    @PostMapping("/accounts/login/{provider}")
    @AuditIgnore
    public ResponseEntity<ApiResponse<UserResponse>> loginSns(
            @Parameter(
                    description = "소셜 로그인 공급자",
                    example = "naver",
                    required = true
            )
            @PathVariable String provider,

            @RequestBody OAuthLoginRequest request
    ) {
        request.setProvider(Provider.of(provider));

        UserWithTokenResult user = userFacade.oauthLogin(userMapper.toDto(request));

        return ApiResponse.success(
                HttpStatus.OK,
                "로그인이 완료되었습니다.",
                userMapper.toResponse(user.getUserResult()),
                user.getHeaders()
        );
    }

    @Operation(summary = "로그아웃", description = "로그아웃 처리")
    @PostMapping("/accounts/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String bearerToken,

            @AuthenticationPrincipal Auth auth
    ) {

        userFacade.logout(bearerToken, auth.getId());

        return ApiResponse.success(
                HttpStatus.OK,
                "로그아웃이 완료되었습니다.",
                null
        );
    }

    @Operation(summary = "비밀번호 검증", description = "비밀번호 검증 처리")
    @PostMapping("/accounts/me/verify-password")
    public ResponseEntity<ApiResponse<Void>> verifyPassword(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String bearerToken,

            @AuthenticationPrincipal Auth auth,
            @RequestBody VerifyPasswordRequest request
    ) {

        userFacade.verifyPassword(auth.getId(), request.getPassword(), bearerToken);

        return ApiResponse.success(
                HttpStatus.OK,
                "비밀번호가 일치합니다.",
                null
        );
    }

    @Operation(summary = "비밀번호 변경", description = "비밀번호 변경 처리")
    @PatchMapping("/accounts/me/password")
    public ResponseEntity<ApiResponse<Void>> updatePassword(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String bearerToken,

            @AuthenticationPrincipal Auth auth,
            @RequestBody UpdatePasswordRequest request
    ) {
        userFacade.updatePassword(auth.getId(), request.getNewPassword(), bearerToken);

        return ApiResponse.success(
                HttpStatus.OK,
                "비밀번호가 변경되었습니다.",
                null
        );
    }

    @Operation(summary = "회원 탈퇴", description = "사용자 탈퇴 처리")
    @DeleteMapping("/accounts/me")
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String bearerToken,

            @AuthenticationPrincipal Auth auth
    ) {

        userFacade.delete(auth.getId(), bearerToken);

        return ApiResponse.success(
                HttpStatus.OK,
                "회원탈퇴가 완료되었습니다.",
                null
        );
    }

    @Operation(summary = "토큰 갱신", description = "AccessToken 갱신")
    @PostMapping("/accounts/refresh")
    public ResponseEntity<ApiResponse<Void>> refresh(
            @Parameter(hidden = true)
            @RequestHeader("Refresh-Token") String refreshToken
    ) {
        return ApiResponse.success(
                HttpStatus.OK,
                "토큰 갱신이 완료되었습니다.",
                null,
                userFacade.refresh(refreshToken)
        );
    }
}
