package com.github.nenidan.ne_ne_challenge.domain.challenge.domain.repository;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.History;

public interface HistoryRepository {

    History save(History history);
}
