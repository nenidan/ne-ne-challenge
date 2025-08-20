package com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.command;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.command.service.ChallengeCommandService;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.ChallengeQueryService;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeStatus;
import com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.dto.request.CreateChallengeRequest;
import com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.dto.request.UpdateChallengeInfoRequest;
import com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.dto.response.ChallengeResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.mapper.ChallengeRequestMapper;
import com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.mapper.ChallengeResponseMapper;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;
import com.github.nenidan.ne_ne_challenge.global.security.auth.Auth;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChallengeController {

    private static final ChallengeRequestMapper requestMapper = ChallengeRequestMapper.INSTANCE;
    private static final ChallengeResponseMapper responseMapper = ChallengeResponseMapper.INSTANCE;

    private final ChallengeCommandService commandService;
    private final ChallengeQueryService queryService;

    @PostMapping("/challenges")
    public ResponseEntity<ApiResponse<ChallengeResponse>> createChallenge(@AuthenticationPrincipal Auth authUser,
        @RequestBody CreateChallengeRequest request
    ) {
        Long challengeId = commandService.createChallenge(authUser.getId(), requestMapper.toCommand(request));

        return ApiResponse.success(HttpStatus.CREATED,
            "챌린지를 생성했습니다.",
            responseMapper.fromDto(queryService.findChallengeById(challengeId))
        );
    }

    @PatchMapping("/challenges/{id}")
    public ResponseEntity<ApiResponse<ChallengeResponse>> updateChallengeInfo(@AuthenticationPrincipal Auth authUser,
        @RequestBody UpdateChallengeInfoRequest request,
        @PathVariable Long id
    ) {
        commandService.updateChallengeInfo(authUser.getId(), id, requestMapper.toCommand(request));

        return ApiResponse.success(HttpStatus.OK,
            "챌린지 정보를 수정했습니다.",
            responseMapper.fromDto(queryService.findChallengeById(id))
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
            responseMapper.fromDto(queryService.findChallengeById(id))
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
            responseMapper.fromDto(queryService.findChallengeById(id))
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
    @GetMapping("/challenges/{id}/participants")
    public ResponseEntity<ApiResponse<CursorResponse<Long, Long>>> searchParticipants(
        @PathVariable Long id,
        @RequestParam(defaultValue = "0") Long cursor,
        @RequestParam(defaultValue = "5") @Min(1) int size
    ){
        return ApiResponse.success(
            HttpStatus.OK,
            "챌린지 참가자 목록을 조회했습니다.",
            queryService.findParticipants(id, cursor, size)
        );
    }
}
