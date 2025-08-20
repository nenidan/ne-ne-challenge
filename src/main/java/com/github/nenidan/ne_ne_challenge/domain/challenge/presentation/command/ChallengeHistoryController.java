package com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.command;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.command.service.ChallengeCommandService;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.ChallengeQueryService;
import com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.dto.request.CreateHistoryRequest;
import com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.dto.response.ChallengeHistoryResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.mapper.ChallengeRequestMapper;
import com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.mapper.ChallengeResponseMapper;
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
public class ChallengeHistoryController {

    private final ChallengeRequestMapper requestMapper = ChallengeRequestMapper.INSTANCE;
    private final ChallengeResponseMapper responseMapper = ChallengeResponseMapper.INSTANCE;

    private final ChallengeCommandService commandService;
    private final ChallengeQueryService queryService;

    @PostMapping("/challenges/{id}/history")
    public ResponseEntity<ApiResponse<ChallengeHistoryResponse>> verifyProgress(@AuthenticationPrincipal Auth authUser,
        @PathVariable("id") Long challengeId,
        @RequestBody CreateHistoryRequest request
    ) {
        Long historyId = commandService.createHistory(authUser.getId(), challengeId, requestMapper.toCommand(request));

        return ApiResponse.success(HttpStatus.CREATED,
            "챌린지 기록을 남겼습니다.",
            responseMapper.fromDto(queryService.findHistoryById(historyId))
        );
    }
}