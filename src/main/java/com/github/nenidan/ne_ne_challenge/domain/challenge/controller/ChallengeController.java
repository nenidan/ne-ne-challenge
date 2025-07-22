package com.github.nenidan.ne_ne_challenge.domain.challenge.controller;

import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.request.ChallengeSearchCond;
import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.request.CreateChallengeRequest;
import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.request.UpdateChallengeRequest;
import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.response.ChallengeResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.service.ChallengeService;
import com.github.nenidan.ne_ne_challenge.domain.challenge.service.ChallengeUserService;
import com.github.nenidan.ne_ne_challenge.domain.user.dto.response.UserResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    private final ChallengeUserService challengeUserService;

    @PostMapping("/challenges")
    public ResponseEntity<ApiResponse<ChallengeResponse>> createChallenge(@RequestBody CreateChallengeRequest request) {
        return ApiResponse.success(HttpStatus.CREATED, "챌린지를 생성했습니다.", challengeService.createChallenge(request));
    }

    @GetMapping("/challenges/{id}")
    public ResponseEntity<ApiResponse<ChallengeResponse>> getChallenge(@PathVariable Long id) {
        return ApiResponse.success(HttpStatus.OK, "챌린지를 조회했습니다.", challengeService.getChallenge(id));
    }

    @GetMapping("/challenges")
    public ResponseEntity<ApiResponse<CursorResponse<ChallengeResponse, LocalDateTime>>> getChallengeList(@ModelAttribute ChallengeSearchCond cond) {
        return ApiResponse.success(HttpStatus.OK, "챌린지 목록을 조회했습니다.", challengeService.getChallengeList(cond));
    }

    @PatchMapping("/challenges/{id}")
    public ResponseEntity<ApiResponse<ChallengeResponse>> updateChallenge(@RequestBody UpdateChallengeRequest request,
        @PathVariable Long id
    ) {
        return ApiResponse.success(HttpStatus.OK, "챌린지 정보를 수정했습니다.", challengeService.updateChallenge(id, request));
    }

    @DeleteMapping("/challenges/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteChallenge(@PathVariable Long id, @RequestParam Long userId) {
        return ApiResponse.success(HttpStatus.OK, "챌린지를 삭제했습니다.", challengeService.deleteChallenge(userId, id));
    }

    @GetMapping("/challenges/{id}/participants")
    public ResponseEntity<ApiResponse<CursorResponse<UserResponse, Long>>> getChallengeParticipantList(@RequestParam(defaultValue = "0") Long cursor,
        @RequestParam(defaultValue = "10") @Min(1) int size,
        @PathVariable Long id
    ) {
        return ApiResponse.success(HttpStatus.OK,
            "챌린지 참가자 목록을 조회했습니다.",
            challengeUserService.getChallengeParticipantList(id, cursor, size)
        );
    }
}