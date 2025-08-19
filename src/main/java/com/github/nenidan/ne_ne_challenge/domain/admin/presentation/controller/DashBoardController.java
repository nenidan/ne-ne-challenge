package com.github.nenidan.ne_ne_challenge.domain.admin.presentation.controller;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.stastics.StatisticsResponse;
import com.github.nenidan.ne_ne_challenge.domain.admin.application.service.StatisticsService;
import com.github.nenidan.ne_ne_challenge.domain.admin.domain.exception.DashboardErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.admin.domain.exception.DashboardException;
import com.github.nenidan.ne_ne_challenge.domain.admin.domain.type.DomainType;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DashBoardController {


    private final StatisticsService statisticsService;

    //통계 관리
    @GetMapping("/admin/statistics/{statisticsType}")
    public ResponseEntity<ApiResponse<StatisticsResponse>> getStatistics(@PathVariable String statisticsType, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime monthPeriod) {

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
