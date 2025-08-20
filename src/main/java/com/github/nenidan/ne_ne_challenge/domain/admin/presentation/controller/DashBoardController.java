package com.github.nenidan.ne_ne_challenge.domain.admin.presentation.controller;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name="통계", description = "통계 조회 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "통계 조회",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(example = """
            {
              "message": "통계 조회",
              "data": {
                "type": "(statisticsType)",
                "createdAt": "2025-08-20T07:39:01.107Z",
                "data1": "1",
                "data2": "2",
                "data3": "3"
              },
              "timestamp": "2025-08-20T07:39:01.107Z"
            }
            """)
                )
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "서버 오류",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(example = """
            {
              "message": "(statisticsType) 통계를 찾을 수 없습니다.",
              "timestamp": "2025-08-20T07:39:01.107Z"
            }
            """)
                )
        )
})
public class DashBoardController {

    private final StatisticsService statisticsService;

    @Operation(summary = "통계 조회", description = "집계된 통계값을 조회합니다.")
    @GetMapping("/admin/statistics/{statisticsType}")
    public ResponseEntity<ApiResponse<StatisticsResponse>> getStatistics(
            @Parameter(name = "statisticsType", description = "어떤 도메인의 통계데이터를 받을지 정해주는 값", in = ParameterIn.PATH, example = "challenge,point,payment,user")
            @PathVariable String statisticsType,
            @Parameter(name = "monthPeriod", description = "yyyy-mm-ddThh:mm:ss의 형식의 파라미터", example = "2025-08-20T07:39:01")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime monthPeriod) {

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
