package com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.controller;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.ChallengeApplicationService;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.dto.request.ChallengeSearchCond;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.dto.request.CreateChallengeRequest;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.dto.request.UpdateChallengeInfoRequest;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.dto.response.ChallengeResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.dto.response.ChallengeSuccessRateResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeStatus;
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
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChallengeController {

    private final ChallengeApplicationService challengeApplicationService;

    @PostMapping("/challenges")
    public ResponseEntity<ApiResponse<ChallengeResponse>> createChallenge(@AuthenticationPrincipal Auth authUser,
        @RequestBody CreateChallengeRequest request
    ) {
        return ApiResponse.success(HttpStatus.CREATED,
            "챌린지를 생성했습니다.",
            challengeApplicationService.createChallenge(request, authUser.getId())
        );
    }

    @GetMapping("/challenges/{id}")
    public ResponseEntity<ApiResponse<ChallengeResponse>> getChallenge(@PathVariable Long id) {
        return ApiResponse.success(HttpStatus.OK, "챌린지를 조회했습니다.", challengeApplicationService.getChallenge(id));
    }

    @PatchMapping("/challenges/{id}")
    public ResponseEntity<ApiResponse<ChallengeResponse>> updateChallengeInfo(@AuthenticationPrincipal Auth authUser,
        @RequestBody UpdateChallengeInfoRequest request,
        @PathVariable Long id
    ) {
        return ApiResponse.success(HttpStatus.OK,
            "챌린지 정보를 수정했습니다.",
            challengeApplicationService.updateChallengeInfo(authUser.getId(), id, request)
        );
    }

    @PutMapping("/challenges/{id}/status")
    public ResponseEntity<ApiResponse<ChallengeResponse>> updateChallengeStatus(@AuthenticationPrincipal Auth authUser,
        @PathVariable Long id,
        @RequestParam ChallengeStatus status
    ) {
        return ApiResponse.success(HttpStatus.OK,
            "챌린지 상태를 수정했습니다.",
            challengeApplicationService.updateChallengeStatus(authUser.getId(), id, status)
        );
    }

    @DeleteMapping("/challenges/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteChallenge(@AuthenticationPrincipal Auth authUser,
        @PathVariable Long id
    ) {
        return ApiResponse.success(HttpStatus.OK,
            "챌린지를 삭제했습니다.",
            challengeApplicationService.deleteChallenge(authUser.getId(), id)
        );
    }

    @PostMapping("/challenges/{id}/join")
    public ResponseEntity<ApiResponse<ChallengeResponse>> joinChallenge(@PathVariable Long id,
        @AuthenticationPrincipal Auth authUser
    ) {
        return ApiResponse.success(HttpStatus.OK,
            "챌린지에 참가했습니다.",
            challengeApplicationService.joinChallenge(authUser.getId(), id)
        );
    }

    @GetMapping("/challenges/{id}/success-rate")
    public ResponseEntity<ApiResponse<ChallengeSuccessRateResponse>> getSuccessRate(@PathVariable Long id,
        @RequestParam Long userId
    ) {
        return ApiResponse.success(HttpStatus.OK,
            "현재까지의 인증율을 조회했습니다.",
            challengeApplicationService.getSuccessRate(userId, id)
        );
    }

    @GetMapping("/challenges")
    public ResponseEntity<ApiResponse<CursorResponse<ChallengeResponse, LocalDateTime>>> getChallengeList(@ModelAttribute ChallengeSearchCond cond) {
        return ApiResponse.success(HttpStatus.OK,
            "챌린지 목록을 조회했습니다.",
            challengeApplicationService.getChallengeList(cond)
        );
    }

}
