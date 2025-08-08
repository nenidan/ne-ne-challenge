package com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.query;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.ChallengeStatisticsService;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.inner.InnerChallengeResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.inner.InnerHistoryResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.inner.InnerParticipantResponse;
import lombok.RequiredArgsConstructor;
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
    public List<InnerHistoryResponse> getAllHistory() {
        return challengeStatisticsService.getAllHistory();
    }
}
