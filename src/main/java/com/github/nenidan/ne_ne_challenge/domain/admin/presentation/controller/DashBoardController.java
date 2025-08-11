package com.github.nenidan.ne_ne_challenge.domain.admin.presentation.controller;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.request.LogSearchCond;
import com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.logs.LogsResponse;
import com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.stastics.StatisticsResponse;
import com.github.nenidan.ne_ne_challenge.domain.admin.application.service.LogQueryService;
import com.github.nenidan.ne_ne_challenge.domain.admin.application.service.StatisticsService;
import com.github.nenidan.ne_ne_challenge.domain.admin.domain.exception.DashboardErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.admin.domain.exception.DashboardException;
import com.github.nenidan.ne_ne_challenge.domain.admin.domain.type.DomainType;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DashBoardController {

    private final LogQueryService logQueryService;
    private final StatisticsService statisticsService;

    //통합 로깅 관리
    @GetMapping("/admin/logs/{logType}")
    public ResponseEntity<ApiResponse<CursorResponse<LogsResponse, LocalDateTime>>> getLogs(@PathVariable String logType, @ModelAttribute LogSearchCond cond) {
        DomainType type;

        try {
            type = DomainType.valueOf(logType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new DashboardException(DashboardErrorCode.INVALID_LOG_TYPE);
        }

        return switch (type) {
            /*case CHALLENGE -> ApiResponse.success(HttpStatus.OK,
                    "챌린지 생성 로그 조회.",
                    logQueryService.getChallengeLogs(cond));*/
            case CHALLENGE -> null;
            case PAYMENT -> ApiResponse.success(HttpStatus.OK,
                    "포인트 충전(결제) 조회.",
                    logQueryService.getPaymentLogs(cond));
            case POINT -> ApiResponse.success(HttpStatus.OK,
                    "포인트 사용 로그 조회.",
                    logQueryService.getPointLogs(cond));
            /*case USER -> ApiResponse.success(HttpStatus.OK,
                    "유저 생성 로그 조회.",
                    logQueryService.getUserLogs(cond));*/
            default -> throw new DashboardException(DashboardErrorCode.INVALID_LOG_TYPE);
            case USER -> null;
        };
    }

    //통계 관리
    @GetMapping("/admin/statistics/{statisticsType}")
    public ResponseEntity<ApiResponse<StatisticsResponse>> getStatistics(@PathVariable String statisticsType, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime monthPeriod) {
        //? extends StatisticsResponse 직렬화 이슈 해결 후 바꾸기
        StatisticsResponse response;
        DomainType type;
        try {
            type = DomainType.valueOf(statisticsType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new DashboardException(DashboardErrorCode.INVALID_LOG_TYPE);
        }

        switch (type) {
            case CHALLENGE -> response = statisticsService.getChallengeStatistics(monthPeriod);
            case PAYMENT -> response = statisticsService.getPaymentStatistics(monthPeriod);
            case POINT -> response = statisticsService.getPointStatistics(monthPeriod);
            case USER  -> response = statisticsService.getUserStatistics(monthPeriod);
            default -> throw new DashboardException(DashboardErrorCode.INVALID_LOG_TYPE);
        }

        return ApiResponse.success(HttpStatus.OK, "통계 조회", response);
    }
}
