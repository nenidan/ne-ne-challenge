package com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.query;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.ChallengeQueryService;
import com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.dto.request.ChallengeSearchRequest;
import com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.dto.request.HistorySearchRequest;
import com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.dto.response.ChallengeHistoryResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.dto.response.ChallengeResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.dto.response.ChallengeSuccessRateResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.mapper.ChallengeRequestMapper;
import com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.mapper.ChallengeResponseMapper;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "챌린지", description = "챌린지 API")
public class ChallengeQueryController {

    private final ChallengeRequestMapper requestMapper = ChallengeRequestMapper.INSTANCE;

    private final ChallengeResponseMapper responseMapper = ChallengeResponseMapper.INSTANCE;

    private final ChallengeQueryService queryService;

    @Operation(summary = "챌린지 단건 조회", description = "id를 기준으로 챌린지를 조회합니다.")
    @GetMapping("/challenges/{id}")
    public ResponseEntity<ApiResponse<ChallengeResponse>> getChallenge(@Parameter(description = "조회할 챌린지 ID", example = "1") @PathVariable Long id) {
        return ApiResponse.success(HttpStatus.OK,
            "챌린지를 조회했습니다.",
            responseMapper.fromDto(queryService.findChallengeById(id))
        );
    }

    @Operation(summary = "챌린지 검색", description = "제공한 조건으로 챌린지 목록을 검색합니다.")
    @GetMapping("/challenges")
    public ResponseEntity<ApiResponse<CursorResponse<ChallengeResponse, LocalDateTime>>> getChallengeList(@ModelAttribute ChallengeSearchRequest request) {
        return ApiResponse.success(HttpStatus.OK,
            "챌린지 목록을 조회했습니다.",
            responseMapper.fromChallengeCursorDto(queryService.getChallengeList(requestMapper.toCond(request)))
        );
    }

    @Operation(summary = "챌린지 인증 기록 조회", description = "사용자의 챌린지 인증 기록을 조회합니다.")
    @GetMapping("/challenges/{id}/history")
    public ResponseEntity<ApiResponse<CursorResponse<ChallengeHistoryResponse, LocalDateTime>>> getHistoryList(
        @Parameter(description = "챌린지 ID", example = "1") @PathVariable("id") Long challengeId,
        @Valid @ModelAttribute HistorySearchRequest request
    ) {
        return ApiResponse.success(HttpStatus.OK,
            "챌린지 기록을 조회했습니다.",
            responseMapper.fromChallengeHistoryCursorDto(queryService.getHistoryList(challengeId,
                requestMapper.toCond(request)
            ))
        );
    }

    @Operation(summary = "현재까지의 인증률 조회", description = "제공한 조건으로 새로운 챌린지를 생성합니다.")
    @GetMapping("/challenges/{id}/success-rate")
    public ResponseEntity<ApiResponse<ChallengeSuccessRateResponse>> getSuccessRate(
        @Parameter(description = "챌린지 ID", example = "1") @PathVariable Long id,
        @Parameter(description = "조회할 사용자 ID", example = "1") @RequestParam Long userId
    ) {
        return ApiResponse.success(HttpStatus.OK,
            "현재까지의 인증율을 조회했습니다.",
            responseMapper.fromDto(queryService.getSuccessRate(userId, id))
        );
    }

    @Operation(summary = "챌린지 참가자 목록 조회", description = "지정한 챌린지 참가자 목록을 페이징하여 조회합니다.")
    @GetMapping("/challenges/{id}/participants")
    public ResponseEntity<ApiResponse<CursorResponse<Long, Long>>> searchParticipants(
        @Parameter(description = "챌린지 ID", example = "1") @PathVariable Long id,

        @Parameter(description = "커서 기반 페이지네이션 키. 키보다 큰 값을 가져옵니다. 기본값은 0입니다.", example = "10")
        @RequestParam(defaultValue = "0") Long cursor,

        @Parameter(description = "한 번에 조회할 데이터 개수. 최소 1 이상, 기본값은 5입니다.", example = "20")
        @RequestParam(defaultValue = "5") @Min(1) int size
    ){
        return ApiResponse.success(
            HttpStatus.OK,
            "챌린지 참가자 목록을 조회했습니다.",
            queryService.findParticipants(id, cursor, size)
        );
    }
}
