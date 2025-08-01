package com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.controller;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.ChallengeApplicationService;
// fixme: 빠른 개발을 위해 application 계층의 DTO 사용
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.dto.request.CreateChallengeRequest;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.dto.response.ChallengeResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import com.github.nenidan.ne_ne_challenge.global.security.auth.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
        return ApiResponse.success(HttpStatus.OK,
            "챌린지를 조회했습니다.",
            challengeApplicationService.getChallenge(id)
        );
    }
}
