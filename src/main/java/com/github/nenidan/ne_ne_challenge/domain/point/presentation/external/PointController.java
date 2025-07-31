package com.github.nenidan.ne_ne_challenge.domain.point.presentation.external;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.point.application.PointFacade;
import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.response.PointHistoryResult;
import com.github.nenidan.ne_ne_challenge.domain.point.presentation.dto.response.PointBalanceResponse;
import com.github.nenidan.ne_ne_challenge.domain.point.presentation.dto.response.PointHistoryResponse;
import com.github.nenidan.ne_ne_challenge.domain.point.presentation.mapper.PointPresentationMapper;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;
import com.github.nenidan.ne_ne_challenge.global.security.auth.Auth;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PointController {

    private final PointFacade pointFacade;

    @GetMapping("/points")
    public ResponseEntity<ApiResponse<PointBalanceResponse>> getMyBalance(@AuthenticationPrincipal Auth auth) {

        PointBalanceResponse response = PointPresentationMapper.toPointBalanceResponse(pointFacade.getMyBalance(auth.getId()));

        return ApiResponse.success(HttpStatus.OK, "포인트 잔액을 조회하였습니다.", response);
    }

    @GetMapping("/points/history")
    public ResponseEntity<ApiResponse<CursorResponse<PointHistoryResponse, Long>>> searchMyPointHistory(
        @AuthenticationPrincipal Auth auth,
        @RequestParam(required = false) Long cursor,
        @RequestParam(defaultValue = "10") @Min(1) int size,
        @RequestParam(required = false) String reason,
        @RequestParam(required = false) LocalDate startDate,
        @RequestParam(required = false) LocalDate endDate) {

        CursorResponse<PointHistoryResult, Long> result = pointFacade.searchMyPointHistory(auth.getId(), cursor, size, reason, startDate, endDate);

        List<PointHistoryResponse> pointHistoryResponseList = result.getContent().stream()
            .map(PointPresentationMapper::toPointHistoryResponse)
            .toList();

        return ApiResponse.success(
            HttpStatus.OK,
            "포인트 이력을 조회하였습니다.",
            new CursorResponse<>(pointHistoryResponseList, result.getNextCursor(), result.isHasNext())
        );
    }
}
