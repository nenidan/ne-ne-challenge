package com.github.nenidan.ne_ne_challenge.domain.point.controller;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.point.dto.response.PointBalanceResponse;
import com.github.nenidan.ne_ne_challenge.domain.point.dto.response.PointHistoryResponse;
import com.github.nenidan.ne_ne_challenge.domain.point.service.PointService;
import com.github.nenidan.ne_ne_challenge.domain.point.type.PointReason;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;
import com.github.nenidan.ne_ne_challenge.global.security.auth.Auth;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PointController {

    private final PointService pointService;

    @GetMapping("/points")
    public ResponseEntity<ApiResponse<PointBalanceResponse>> getMyBalance(@RequestParam Long userId) {
        return ApiResponse.success(HttpStatus.OK, "포인트 잔액을 조회하였습니다.", pointService.getMyBalance(userId));
    }

    @GetMapping("/points/history")
    public ResponseEntity<ApiResponse<CursorResponse<PointHistoryResponse, Long>>> searchMyPointHistory(
        @RequestParam Long userId,
        @RequestParam(required = false) Long cursor,
        @RequestParam(defaultValue = "10") @Min(1) int size,
        @RequestParam(required = false) PointReason reason,
        @RequestParam(required = false) LocalDate startDate,
        @RequestParam(required = false) LocalDate endDate) {

        return ApiResponse.success(
            HttpStatus.OK,
            "포인트 이력을 조회하였습니다.",
            pointService.searchMyPointHistory(userId, cursor, size, reason, startDate, endDate)
        );
    }
}
