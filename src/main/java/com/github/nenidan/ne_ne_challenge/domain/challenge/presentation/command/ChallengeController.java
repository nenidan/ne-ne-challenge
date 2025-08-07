package com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.command;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.command.dto.CreateChallengeCommand;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.command.dto.UpdateChallengeInfoCommand;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.command.service.ChallengeCommandService;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.ChallengeQueryService;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.response.ChallengeResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeStatus;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import com.github.nenidan.ne_ne_challenge.global.security.auth.Auth;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChallengeController {

    private final ChallengeCommandService commandService;
    private final ChallengeQueryService queryService;

    @PostMapping("/challenges")
    public ResponseEntity<ApiResponse<ChallengeResponse>> createChallenge(@AuthenticationPrincipal Auth authUser,
        @RequestBody CreateChallengeCommand request
    ) {
        Long challengeId = commandService.createChallenge(authUser.getId(), request);

        return ApiResponse.success(HttpStatus.CREATED,
            "챌린지를 생성했습니다.",
            queryService.findChallengeById(challengeId)
        );
    }

    @PatchMapping("/challenges/{id}")
    public ResponseEntity<ApiResponse<ChallengeResponse>> updateChallengeInfo(@AuthenticationPrincipal Auth authUser,
        @RequestBody UpdateChallengeInfoCommand request,
        @PathVariable Long id
    ) {
        commandService.updateChallengeInfo(authUser.getId(), id, request);

        return ApiResponse.success(HttpStatus.OK,
            "챌린지 정보를 수정했습니다.",
            queryService.findChallengeById(id)
        );
    }

    @PutMapping("/challenges/{id}/status")
    public ResponseEntity<ApiResponse<ChallengeResponse>> updateChallengeStatus(@AuthenticationPrincipal Auth authUser,
        @PathVariable Long id,
        @RequestParam ChallengeStatus status
    ) {
        commandService.updateChallengeStatus(authUser.getId(), id, status);

        return ApiResponse.success(HttpStatus.OK,
            "챌린지 상태를 수정했습니다.",
            queryService.findChallengeById(id)
        );
    }

    @DeleteMapping("/challenges/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteChallenge(@AuthenticationPrincipal Auth authUser,
        @PathVariable Long id
    ) {
        commandService.deleteChallenge(authUser.getId(), id);

        return ApiResponse.success(HttpStatus.OK,
            "챌린지를 삭제했습니다.",
            null
        );
    }

    @PostMapping("/challenges/{id}/join")
    public ResponseEntity<ApiResponse<ChallengeResponse>> joinChallenge(@PathVariable Long id,
        @AuthenticationPrincipal Auth authUser
    ) {
        commandService.joinChallenge(authUser.getId(), id);

        return ApiResponse.success(HttpStatus.OK,
            "챌린지에 참가했습니다.",
            queryService.findChallengeById(id)
        );
    }

    @PostMapping("/challenges/{id}/quit")
    public ResponseEntity<ApiResponse<Void>> quitChallenge(@PathVariable Long id,
        @AuthenticationPrincipal Auth authUser
    ) {
        commandService.quitChallenge(authUser.getId(), id);

        return ApiResponse.success(HttpStatus.OK,
            "챌린지를 나왔습니다.",
            null
        );
    }
}
