package com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.query;

//import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.ChallengeStatisticsService;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.inner.InnerChallengeResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.inner.InnerHistoryResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.inner.InnerParticipantResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api")
//public class ChallengeStatisticsController {
//
//    private final ChallengeStatisticsService challengeStatisticsService;
//
//    @GetMapping("/statistics/challenges")
//    public ResponseEntity<ApiResponse<List<InnerChallengeResponse>>> getAllChallenges() {
//        return ApiResponse.success(
//            HttpStatus.OK,
//            "전체 챌린지 목록",
//            challengeStatisticsService.getAllChallenge()
//        );
//    }
//
//    @GetMapping("/statistics/participants")
//    public ResponseEntity<ApiResponse<List<InnerParticipantResponse>>> getAllParticipants() {
//        return ApiResponse.success(
//            HttpStatus.OK,
//            "전체 참가자 목록",
//            challengeStatisticsService.getAllParticipant()
//        );
//    }
//
//    @GetMapping("/statistics/history")
//    public ResponseEntity<ApiResponse<List<InnerHistoryResponse>>> getAllHistory() {
//        return ApiResponse.success(
//            HttpStatus.OK,
//            "전체 인증 기록 목록",
//            challengeStatisticsService.getAllHistory()
//        );
//    }
//}
