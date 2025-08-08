package com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.controller;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.ChallengeStatisticsService;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.dto.response.inner.InnerChallengeResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.dto.response.inner.InnerHistoryResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.dto.response.inner.InnerParticipantResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal")
public class ChallengeStatisticsController {

    private final ChallengeStatisticsService challengeStatisticsService;

    @GetMapping("/statistics/challenges")
    public List<InnerChallengeResponse> getAllChallenges() {
        return challengeStatisticsService.getAllChallenge();
    }

    @GetMapping("/statistics/participants")
    public List<InnerParticipantResponse> getAllParticipants() {
        return challengeStatisticsService.getAllParticipant();
    }

    @GetMapping("/statistics/history")
    public ResponseEntity<ApiResponse<List<InnerHistoryResponse>>> getAllHistory() {
        return ApiResponse.success(
            HttpStatus.OK,
            "전체 인증 기록 목록",
            challengeStatisticsService.getAllHistory()
        );
    }
}
