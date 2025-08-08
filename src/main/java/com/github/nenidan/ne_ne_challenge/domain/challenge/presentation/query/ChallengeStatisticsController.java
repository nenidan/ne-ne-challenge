package com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.query;

//import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.ChallengeStatisticsService;
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
