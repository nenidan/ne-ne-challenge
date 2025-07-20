package com.github.nenidan.ne_ne_challenge.domain.user.controller;

import com.github.nenidan.ne_ne_challenge.domain.user.dto.request.JoinRequest;
import com.github.nenidan.ne_ne_challenge.domain.user.dto.response.UserResponse;
import com.github.nenidan.ne_ne_challenge.domain.user.service.UserService;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import com.github.nenidan.ne_ne_challenge.global.security.auth.Auth;
import com.github.nenidan.ne_ne_challenge.global.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/accounts")
    public ResponseEntity<ApiResponse<UserResponse>> join(@RequestBody JoinRequest joinRequest) {

        UserResponse user = userService.join(joinRequest);

        return ApiResponse.success(
                HttpStatus.OK,
                "회원가입이 완료되었습니다.",
                user,
                createAuthHeaders(user)
        );
    }




    public HttpHeaders createAuthHeaders(UserResponse user) {

        Auth auth = new Auth(user.getId(), user.getNickname(), user.getRole());

        String token = jwtUtil.createToken(auth);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwtUtil.getBearerToken(token));

        return headers;
    }


}
