package com.github.nenidan.ne_ne_challenge.domain.admin.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;

import com.github.nenidan.ne_ne_challenge.domain.admin.domain.type.DomainType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StatisticDataModel {
    private final Long id;
    private final DomainType type;
    private final LocalDate statDate;
    private final String payload;
    private final LocalDateTime createdAt;

    public static StatisticDataModel of(Long id, DomainType type, LocalDate statDate, String payload, LocalDateTime createdAt) {
        return new StatisticDataModel(id, type, statDate, payload, createdAt);
    }
}
