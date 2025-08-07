package com.github.nenidan.ne_ne_challenge.domain.challenge.infrastructure.repository;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.History;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class HistoryRepositoryImpl implements HistoryRepository {

    private final JpaHistoryRepository jpaHistoryRepository;

    @Override
    public History save(History history) {
        return jpaHistoryRepository.save(history);
    }

    @Override
    public Map<Long, Integer> countHistory(List<Long> userIdList, Long challengeId) {
        List<Object[]> results = jpaHistoryRepository.countUserHistory(userIdList, challengeId);

        Map<Long, Integer> countMap = new HashMap<>();
        for (Object[] result : results) {
            Long userId = (Long) result[0];
            Long count = (Long) result[1];
            countMap.put(userId, count.intValue());
        }

        return countMap;
    }
}