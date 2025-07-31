package com.github.nenidan.ne_ne_challenge.domain.admin.controller;


import com.github.nenidan.ne_ne_challenge.domain.admin.dto.request.LogSearchCond;
import com.github.nenidan.ne_ne_challenge.domain.admin.dto.response.LogsResponse;
import com.github.nenidan.ne_ne_challenge.domain.admin.exception.LogErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.admin.exception.LogQueryException;
import com.github.nenidan.ne_ne_challenge.domain.admin.service.LogQueryService;
import com.github.nenidan.ne_ne_challenge.domain.admin.type.LogType;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DashBoardController {

    private final LogQueryService logQueryService;

    //통합 로깅 관리
    @GetMapping("/admin/logs/{logType}")
    public ResponseEntity<ApiResponse<CursorResponse<LogsResponse, LocalDateTime>>> getLogs(@PathVariable String logType, @ModelAttribute LogSearchCond cond) {
        LogType type;

        try {
            type = LogType.valueOf(logType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new LogQueryException(LogErrorCode.INVALID_LOG_TYPE);
        }

        return switch (type) {
            /*case CHALLENGE -> ApiResponse.success(HttpStatus.OK,
                    "챌린지 생성 로그 조회.",
                    logQueryService.getChallengeLogs(cond));*/
            case CHALLENGE -> null;
            case PAYMENT -> ApiResponse.success(HttpStatus.OK,
                    "포인트 충전(결제) 조회.",
                    logQueryService.getPaymentLogs(cond));
            /*case POINT -> ApiResponse.success(HttpStatus.OK,
                    "포인트 사용 로그 조회.",
                    logQueryService.getPointLogs(cond));
            case USER -> ApiResponse.success(HttpStatus.OK,
                    "유저 생성 로그 조회.",
                    logQueryService.getUserLogs(cond));*/
            //default -> throw new LogQueryException(LogErrorCode.INVALID_LOG_TYPE);
            case POINT -> null;
            case USER -> null;
        };
    }
}
