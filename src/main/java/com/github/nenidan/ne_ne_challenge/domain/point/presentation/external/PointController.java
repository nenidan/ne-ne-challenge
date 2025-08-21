package com.github.nenidan.ne_ne_challenge.domain.point.presentation.external;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "포인트", description = "포인트 API")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class PointController {

    private final PointFacade pointFacade;

    @Operation(summary = "포인트 잔액 조회", description = "본인 포인트 지갑의 잔액을 조회합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "포인트 잔액을 조회하였습니다."),
    })
    @GetMapping("/points")
    public ResponseEntity<ApiResponse<PointBalanceResponse>> getMyBalance(
        @Parameter(hidden = true) @AuthenticationPrincipal Auth auth) {

        PointBalanceResponse response = PointPresentationMapper.toPointBalanceResponse(pointFacade.getMyBalance(auth.getId()));

        return ApiResponse.success(HttpStatus.OK, "포인트 잔액을 조회하였습니다.", response);
    }

    @Operation(summary = "포인트 사용 이력 조회", description = "본인 포인트 사용 이력을 조회합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "포인트 이력을 조회하였습니다."),
    })
    @GetMapping("/points/history")
    public ResponseEntity<ApiResponse<CursorResponse<PointHistoryResponse, Long>>> searchMyPointHistory(
        @Parameter(hidden = true) @AuthenticationPrincipal Auth auth,
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
