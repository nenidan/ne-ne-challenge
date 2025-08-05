package com.github.nenidan.ne_ne_challenge.domain.challenge.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.History;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.repository.HistoryReposiroty;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class HistoryRepositoryImpl implements HistoryReposiroty {

    private final JpaHistoryRepository jpaHistoryRepository;

    @Override
    public History save(History history) {
        return jpaHistoryRepository.save(history);
    }

    @Override
    public List<History> getHistoryListByChallengeId(Long challengeId) {
        return jpaHistoryRepository.findByChallenge_Id(challengeId);
    }

    @Override
    public Long countTodayHistory(Long userId, Long challengeId, LocalDateTime startOfDay, LocalDateTime endOfDay) {
        return jpaHistoryRepository.countTodayHistory(userId, challengeId, startOfDay, endOfDay);
    }

    @Override
    public List<History> getHistoryList(Long challengeId, Long userId, LocalDateTime cursor, int lim) {
        return jpaHistoryRepository.getChallengeHistoryList(challengeId, userId, cursor, lim+1);
    }

    @Override
    public int countOfSuccess(Long challengeId, Long userId) {
        return jpaHistoryRepository.countByChallenge_IdAndUserIdAndIsSuccessTrue(challengeId, userId);
    }

    @Override
    public List<History> findAll() {
        return jpaHistoryRepository.findAll();
    }

}