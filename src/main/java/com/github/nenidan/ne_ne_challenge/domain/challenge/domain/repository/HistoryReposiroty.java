package com.github.nenidan.ne_ne_challenge.domain.challenge.domain.repository;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.History;

import java.time.LocalDateTime;
import java.util.List;

public interface HistoryReposiroty {

    History save(History history);

    List<History> getHistoryListByChallengeId(Long challengeId);

    Long countTodayHistory(Long userId, Long challengeId, LocalDateTime startOfDay, LocalDateTime endOfDay);

    List<History> getHistoryList(Long challengeId, Long userId, LocalDateTime cursor, int limit);

    int countOfSuccess(Long challengeId, Long userId);
}
