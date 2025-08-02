package com.github.nenidan.ne_ne_challenge.domain.user.presentation.controller.external;

import com.github.nenidan.ne_ne_challenge.domain.user.application.UserFacade;
import com.github.nenidan.ne_ne_challenge.domain.user.application.dto.UserWithTokenResult;
import com.github.nenidan.ne_ne_challenge.domain.user.presentation.dto.request.JoinRequest;
import com.github.nenidan.ne_ne_challenge.domain.user.presentation.dto.request.LoginRequest;
import com.github.nenidan.ne_ne_challenge.domain.user.presentation.dto.response.UserResponse;
import com.github.nenidan.ne_ne_challenge.domain.user.presentation.mapper.UserMapper;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
