package com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.controller;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.ChallengeApplicationService;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.dto.request.CreateHistoryRequest;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.dto.request.HistorySearchCond;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.dto.response.ChallengeHistoryResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;
import com.github.nenidan.ne_ne_challenge.global.security.auth.Auth;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChallengeHistoryController {

    private final ChallengeApplicationService challengeApplicationService;

    @PostMapping("/challenges/{id}/history")
    public ResponseEntity<ApiResponse<ChallengeHistoryResponse>> verifyProgress(@AuthenticationPrincipal Auth authUser,
        @PathVariable Long id,
        @RequestBody CreateHistoryRequest request
    ) {
        return ApiResponse.success(HttpStatus.CREATED,
            "챌린지 기록을 남겼습니다.",
            challengeApplicationService.createHistory(request, authUser.getId(), id)
        );
    }

    @GetMapping("/challenges/{id}/history")
    public ResponseEntity<ApiResponse<CursorResponse<ChallengeHistoryResponse, LocalDateTime>>> getHistoryList(@PathVariable Long id,
        @Valid @ModelAttribute HistorySearchCond cond
    ) {
        return ApiResponse.success(HttpStatus.OK,
            "챌린지 기록을 조회했습니다.",
            challengeApplicationService.getHistoryList(id, cond)
        );
    }
}
