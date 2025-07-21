package com.github.nenidan.ne_ne_challenge.domain.challenge.controller;

import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.request.ChallengeSearchCond;
import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.request.CreateChallengeRequest;
import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.request.UpdateChallengeRequest;
import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.response.ChallengeResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.service.ChallengeService;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;
import com.github.nenidan.ne_ne_challenge.global.security.auth.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    @PostMapping("/challenges")
    public ResponseEntity<ApiResponse<ChallengeResponse>> createChallenge(@AuthenticationPrincipal Auth authUser,
        @RequestBody CreateChallengeRequest request
    ) {
        return ApiResponse.success(HttpStatus.CREATED,
            "챌린지를 생성했습니다.",
            challengeService.createChallenge(request, authUser.getId())
        );
    }

    @GetMapping("/challenges/{id}")
    public ResponseEntity<ApiResponse<ChallengeResponse>> getChallenge(@PathVariable Long id) {
        return ApiResponse.success(HttpStatus.OK,
            "챌린지를 조회했습니다.",
            challengeService.getChallenge(id)
        );
    }

    @GetMapping("/challenges")
    public ResponseEntity<ApiResponse<CursorResponse<ChallengeResponse, LocalDateTime>>> getChallengeList(@ModelAttribute ChallengeSearchCond cond) {
        return ApiResponse.success(HttpStatus.OK,
            "챌린지 목록을 조회했습니다.",
            challengeService.getChallengeList(cond)
        );
    }

    @PatchMapping("/challenges/{id}")
    public ResponseEntity<ApiResponse<ChallengeResponse>> updateChallenge(@AuthenticationPrincipal Auth authUser, @RequestBody UpdateChallengeRequest request, @PathVariable Long id) {
        return ApiResponse.success(HttpStatus.OK,
            "챌린지 정보를 수정했습니다.",
            challengeService.updateChallenge(authUser.getId(), id, request)
        );
    }
}