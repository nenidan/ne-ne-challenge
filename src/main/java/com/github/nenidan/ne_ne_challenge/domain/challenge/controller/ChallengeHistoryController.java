package com.github.nenidan.ne_ne_challenge.domain.challenge.controller;

import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.request.CreateHistoryRequest;
import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.response.ChallengeHistoryResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.service.ChallengeHistoryService;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import com.github.nenidan.ne_ne_challenge.global.security.auth.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChallengeHistoryController {

    private final ChallengeHistoryService challengeHistoryService;

    @PostMapping("/challenges/{id}/history")
    public ResponseEntity<ApiResponse<ChallengeHistoryResponse>> verifyProgress(@AuthenticationPrincipal Auth authUser,
        @PathVariable Long id,
        @RequestBody CreateHistoryRequest request
    ) {
        return ApiResponse.success(HttpStatus.CREATED,
            "챌린지 기록을 남겼습니다.",
            challengeHistoryService.createHistory(request, authUser.getId(), id)
        );
    }
}