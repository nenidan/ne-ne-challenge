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

public class ChallengeController {

    private static final ChallengeRequestMapper requestMapper = ChallengeRequestMapper.INSTANCE;
    private static final ChallengeResponseMapper responseMapper = ChallengeResponseMapper.INSTANCE;

    private final ChallengeCommandService commandService;
    private final ChallengeQueryService queryService;

    @Operation(summary = "새로운 챌린지 생성", description = "제공한 조건으로 새로운 챌린지를 생성합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "새로운 챌린지 정상 생성.")
    })
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

    @Operation(summary = "챌린지 정보 수정", description = "대기중(WAITING)인 챌린지의 이름, 설명, 시작/종료일, 카테고리를 수정합니다. 방장만 가능합니다.")
    @PatchMapping("/challenges/{id}")
    public ResponseEntity<ApiResponse<ChallengeResponse>> updateChallengeInfo(@AuthenticationPrincipal Auth authUser,
        @RequestBody UpdateChallengeInfoRequest request,
        @Parameter(description = "챌린지 ID", example = "1") @PathVariable Long id
    ) {
        commandService.updateChallengeInfo(authUser.getId(), id, requestMapper.toCommand(request));

        return ApiResponse.success(HttpStatus.OK,
            "챌린지 정보를 수정했습니다.",
            responseMapper.fromDto(queryService.findChallengeById(id))
        );
    }

    @Operation(summary = "챌린지 상태 변경", description = "참가자 확정(READY) 또는 챌린지 시작(READY)을 요청합니다.")
    @PutMapping("/challenges/{id}/status")
    public ResponseEntity<ApiResponse<ChallengeResponse>> updateChallengeStatus(@AuthenticationPrincipal Auth authUser,
        @Parameter(description = "챌린지 ID", example = "1") @PathVariable Long id,
        @Parameter(description = "변경할 챌린지 상태") @RequestParam ChallengeStatus status
    ) {
        commandService.updateChallengeStatus(authUser.getId(), id, status);

        return ApiResponse.success(HttpStatus.OK,
            "챌린지 상태를 수정했습니다.",
            responseMapper.fromDto(queryService.findChallengeById(id))
        );
    }

    @Operation(summary = "챌린지 삭제", description = "아직 시작하지 않은 챌린지를 삭제합니다.")
    @DeleteMapping("/challenges/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteChallenge(@AuthenticationPrincipal Auth authUser,
        @Parameter(description = "챌린지 ID", example = "1") @PathVariable Long id
    ) {
        commandService.deleteChallenge(authUser.getId(), id);

        return ApiResponse.success(HttpStatus.OK,
            "챌린지를 삭제했습니다.",
            null
        );
    }

    @Operation(summary = "챌린지 참가", description = "지정한 챌린지에 참가합니다.")
    @PostMapping("/challenges/{id}/join")
    public ResponseEntity<ApiResponse<ChallengeResponse>> joinChallenge(@Parameter(description = "챌린지 ID", example = "1") @PathVariable Long id,
        @AuthenticationPrincipal Auth authUser
    ) {
        commandService.joinChallenge(authUser.getId(), id);

        return ApiResponse.success(HttpStatus.OK,
            "챌린지에 참가했습니다.",
            responseMapper.fromDto(queryService.findChallengeById(id))
        );
    }

    @Operation(summary = "챌린지 퇴장", description = "참가중인 챌린지에서 퇴장합니다.")
    @PostMapping("/challenges/{id}/quit")
    public ResponseEntity<ApiResponse<Void>> quitChallenge(@Parameter(description = "챌린지 ID", example = "1") @PathVariable Long id,
        @AuthenticationPrincipal Auth authUser
    ) {
        commandService.quitChallenge(authUser.getId(), id);

        return ApiResponse.success(HttpStatus.OK,
            "챌린지를 나왔습니다.",
            null
        );
    }
}
