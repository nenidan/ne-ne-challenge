package com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.repository;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.request.HistorySearchCond;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.response.ChallengeHistoryResponse;

import java.util.List;
import java.util.Optional;

public interface ChallengeHistoryQueryRepository {

    Optional<ChallengeHistoryResponse> findHistoryById(Long historyId);

    List<ChallengeHistoryResponse> findHistory(Long challengeId, HistorySearchCond cond);

    Long countByChallengeIdAndUserId(Long challengeId, Long userId);
}
