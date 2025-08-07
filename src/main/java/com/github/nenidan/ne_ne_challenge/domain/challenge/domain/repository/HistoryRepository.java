package com.github.nenidan.ne_ne_challenge.domain.challenge.domain.repository;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.History;

import java.util.List;
import java.util.Map;

public interface HistoryRepository {

    History save(History history);

    Map<Long, Integer> countHistory(List<Long> userIdList, Long challengeId);
}
