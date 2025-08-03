package com.github.nenidan.ne_ne_challenge.domain.challenge.domain.service;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.History;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.repository.HistoryReposiroty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryReposiroty historyReposiroty;

    public List<History> getHistoryListByChallengeId(Long challengeId) {
        return historyReposiroty.getHistoryListByChallengeId(challengeId);
    }

    public void delete(History history) {
        history.delete();
    }

    public Long countTodayHistory(Long userId,
        Long challengeId,
        LocalDateTime startOfDay,
        LocalDateTime endOfDay
    ) {
        return historyReposiroty.countTodayHistory(userId, challengeId, startOfDay, endOfDay);
    }

    public History save(History history) {
        return historyReposiroty.save(history);
    }

    public List<History> getHistoryList(Long challengeId, Long userId, LocalDateTime cursor, int size) {
        return historyReposiroty.getHistoryList(challengeId, userId, cursor, size + 1);
    }

    public int countOfSuccess(Long challengeId, Long userId) {
        return historyReposiroty.countOfSuccess(challengeId, userId);
    }
}
