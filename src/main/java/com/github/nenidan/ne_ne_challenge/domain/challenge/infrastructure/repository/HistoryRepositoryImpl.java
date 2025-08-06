package com.github.nenidan.ne_ne_challenge.domain.challenge.infrastructure.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.History;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.repository.HistoryRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class HistoryRepositoryImpl implements HistoryRepository {

    private final JpaHistoryRepository jpaHistoryRepository;

    @Override
    public History save(History history) {
        return jpaHistoryRepository.save(history);
    }
}