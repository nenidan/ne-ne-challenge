package com.github.nenidan.ne_ne_challenge.domain.challenge.controller;

import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.request.CreateChallengeRequest;
import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.response.ChallengeResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.service.ChallengeService;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import com.github.nenidan.ne_ne_challenge.global.security.auth.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    @PostMapping("/challenges")
    public ResponseEntity<ApiResponse<ChallengeResponse>> createChallenge(@AuthenticationPrincipal Auth auth,
        @RequestBody CreateChallengeRequest request
    ) {
        return ApiResponse.success(HttpStatus.CREATED,
            "챌린지를 생성했습니다.",
            challengeService.createChallenge(request, auth.getId())
        );
    }
}