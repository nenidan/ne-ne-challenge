package com.github.nenidan.ne_ne_challenge.domain.challenge.application.query;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.*;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChallengeQueryService {

    public ChallengeResponse findChallengeById(Long challengeId) {
        return null; // Todo
    }

    public CursorResponse<ChallengeResponse, LocalDateTime> getChallengeList(ChallengeSearchCond cond) {
        return null; // Todo
    }

    public ChallengeSuccessRateResponse getSuccessRate(Long userId, Long challengeId) {
        return null; // Todo
    }

    public ChallengeHistoryResponse findHistoryById(Long historyId) {
        return null; // Todo
    }

    public CursorResponse<ChallengeHistoryResponse, LocalDateTime> getHistoryList(Long challengeId, HistorySearchCond cond) {
        return null; // Todo
    }
}
