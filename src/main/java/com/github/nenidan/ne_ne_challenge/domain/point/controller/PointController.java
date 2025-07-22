package com.github.nenidan.ne_ne_challenge.domain.point.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.point.dto.response.PointBalanceResponse;
import com.github.nenidan.ne_ne_challenge.domain.point.service.PointService;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import com.github.nenidan.ne_ne_challenge.global.security.auth.Auth;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PointController {

    private final PointService pointService;

    @GetMapping("/points")
    public ResponseEntity<ApiResponse<PointBalanceResponse>> getMyBalance(@AuthenticationPrincipal Auth auth) {
        return ApiResponse.success(HttpStatus.OK, "포인트 잔액을 조회하였습니다.", pointService.getMyBalance(auth.getId()));
    }

}
