package com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.query;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.ChallengeQueryService;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.*;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChallengeQueryController {

    private final ChallengeQueryService queryService;

    @GetMapping("/challenges/{id}")
    public ResponseEntity<ApiResponse<ChallengeResponse>> getChallenge(@PathVariable Long id) {
        return ApiResponse.success(HttpStatus.OK, "챌린지를 조회했습니다.", queryService.findChallengeById(id));
    }

    @GetMapping("/challenges/{id}/success-rate")
    public ResponseEntity<ApiResponse<ChallengeSuccessRateResponse>> getSuccessRate(@PathVariable Long id,
        @RequestParam Long userId
    ) {
        return ApiResponse.success(HttpStatus.OK,
            "현재까지의 인증율을 조회했습니다.",
            queryService.getSuccessRate(userId, id)
        );
    }

    @GetMapping("/challenges")
    public ResponseEntity<ApiResponse<CursorResponse<ChallengeResponse, LocalDateTime>>> getChallengeList(@ModelAttribute ChallengeSearchCond cond) {
        return ApiResponse.success(HttpStatus.OK,
            "챌린지 목록을 조회했습니다.",
            queryService.getChallengeList(cond)
        );
    }

    @GetMapping("/challenges/{id}/history")
    public ResponseEntity<ApiResponse<CursorResponse<ChallengeHistoryResponse, LocalDateTime>>> getHistoryList(
        @PathVariable("id") Long challengeId,
        @Valid @ModelAttribute HistorySearchCond cond
    ) {
        return ApiResponse.success(HttpStatus.OK,
            "챌린지 기록을 조회했습니다.",
            queryService.getHistoryList(challengeId, cond)
        );
    }
}
