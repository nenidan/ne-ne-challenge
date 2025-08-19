package com.github.nenidan.ne_ne_challenge.domain.user.presentation.controller.external;

import com.github.nenidan.ne_ne_challenge.global.aop.annotation.AuditIgnore;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountController {

    private final UserFacade userFacade;
    private final UserMapper userMapper;

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

    @PostMapping("/accounts/login/{provider}")
    @AuditIgnore
    public ResponseEntity<ApiResponse<UserResponse>> loginSns(
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

    @PostMapping("/accounts/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
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

    @PostMapping("/accounts/me/verify-password")
    public ResponseEntity<ApiResponse<Void>> verifyPassword(
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

    @PatchMapping("/accounts/me/password")
    public ResponseEntity<ApiResponse<Void>> updatePassword(
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

    @DeleteMapping("/accounts/me")
    public ResponseEntity<ApiResponse<Void>> delete(
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

    @PostMapping("/accounts/refresh")
    public ResponseEntity<ApiResponse<Void>> refresh(
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
