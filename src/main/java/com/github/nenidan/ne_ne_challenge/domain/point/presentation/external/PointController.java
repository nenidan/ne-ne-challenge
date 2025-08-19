package com.github.nenidan.ne_ne_challenge.domain.point.presentation.external;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.point.application.PointFacade;
import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.response.PointHistoryResult;
import com.github.nenidan.ne_ne_challenge.domain.point.presentation.dto.request.PointSearchRequest;
import com.github.nenidan.ne_ne_challenge.domain.point.presentation.dto.response.PointBalanceResponse;
import com.github.nenidan.ne_ne_challenge.domain.point.presentation.dto.response.PointHistoryResponse;
import com.github.nenidan.ne_ne_challenge.domain.point.presentation.mapper.PointPresentationMapper;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;
import com.github.nenidan.ne_ne_challenge.global.security.auth.Auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PointController {

    private final PointFacade pointFacade;

    /**
     * 자신의 포인트 지갑의 잔액을 확인하는 API
     * @param auth 인증된 사용자 정보
     * @return 자신의 포인트 잔액
     */
    @GetMapping("/points")
    public ResponseEntity<ApiResponse<PointBalanceResponse>> getMyBalance(@AuthenticationPrincipal Auth auth) {

        PointBalanceResponse response = PointPresentationMapper.toPointBalanceResponse(pointFacade.getMyBalance(auth.getId()));

        return ApiResponse.success(HttpStatus.OK, "포인트 잔액을 조회하였습니다.", response);
    }

    /**
     * 자신의 포인트 사용 이력을 조회하는 API
     * @param auth 인증된 사용자 정보
     * @param request 포인트 사용 이력 검색 조건(cursor, size, reason, startDate, endDate)
     * @return 커서 기반의 자신의 포인트 사용 이력
     */
    @GetMapping("/points/history")
    public ResponseEntity<ApiResponse<CursorResponse<PointHistoryResponse, Long>>> searchMyPointHistory(
        @AuthenticationPrincipal Auth auth,
        @Valid PointSearchRequest request) {

        CursorResponse<PointHistoryResult, Long> result = pointFacade.searchMyPointHistory(
            auth.getId(),
            PointPresentationMapper.toPointSearchCommand(request)
        );

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
