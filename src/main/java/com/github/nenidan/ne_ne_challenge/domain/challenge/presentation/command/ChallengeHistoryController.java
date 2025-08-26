package com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.command;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.command.service.ChallengeCommandService;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.ChallengeQueryService;
import com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.dto.request.CreateHistoryRequest;
import com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.dto.response.ChallengeHistoryResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.mapper.ChallengeRequestMapper;
import com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.mapper.ChallengeResponseMapper;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import com.github.nenidan.ne_ne_challenge.global.security.auth.Auth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "챌린지", description = "챌린지 API")
public class ChallengeHistoryController {

    private final ChallengeRequestMapper requestMapper = ChallengeRequestMapper.INSTANCE;
    private final ChallengeResponseMapper responseMapper = ChallengeResponseMapper.INSTANCE;

    private final ChallengeCommandService commandService;
    private final ChallengeQueryService queryService;

    @Operation(summary = "챌린지 인증 기록 생성", description = "오늘의 챌린지 인증 기록을 남깁니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "인증 기록 정상 생성")
    })
    @PostMapping("/challenges/{id}/history")
    public ResponseEntity<ApiResponse<ChallengeHistoryResponse>> verifyProgress(@AuthenticationPrincipal Auth authUser,
        @Parameter(description = "챌린지 ID", example = "1") @PathVariable("id") Long challengeId,
        @RequestBody CreateHistoryRequest request
    ) {
        Long historyId = commandService.createHistory(authUser.getId(), challengeId, requestMapper.toCommand(request));

        return ApiResponse.success(HttpStatus.CREATED,
            "챌린지 기록을 남겼습니다.",
            responseMapper.fromDto(queryService.findHistoryById(historyId))
        );
    }
}