package com.github.nenidan.ne_ne_challenge.domain.challenge.controller;

import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.request.CreateHistoryRequest;
import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.request.HistorySearchCond;
import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.response.ChallengeHistoryResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.service.ChallengeHistoryService;
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
public class ChallengeHistoryController {

    private final ChallengeHistoryService challengeHistoryService;

    @PostMapping("/challenges/{id}/history")
    public ResponseEntity<ApiResponse<ChallengeHistoryResponse>> verifyProgress(@PathVariable Long id,
        @RequestBody CreateHistoryRequest request
    ) {
        return ApiResponse.success(HttpStatus.CREATED,
            "챌린지 기록을 남겼습니다.",
            challengeHistoryService.createHistory(request, id)
        );
    }

    @GetMapping("/challenges/{id}/history")
    public ResponseEntity<ApiResponse<CursorResponse<ChallengeHistoryResponse, LocalDateTime>>> getHistoryList(@PathVariable Long id,
        @ModelAttribute HistorySearchCond cond
    ) {
        return ApiResponse.success(HttpStatus.OK,
            "챌린지 기록을 조회했습니다.",
            challengeHistoryService.getHistoryList(id, cond)
        );
    }
}