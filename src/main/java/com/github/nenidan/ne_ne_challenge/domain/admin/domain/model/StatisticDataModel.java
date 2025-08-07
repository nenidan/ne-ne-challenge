package com.github.nenidan.ne_ne_challenge.domain.admin.domain.model;

import java.time.LocalDate;

import com.github.nenidan.ne_ne_challenge.domain.admin.domain.type.DomainType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StatisticDataModel {
    private final Long id;
    private final DomainType type;
    private final LocalDate statDate;
    private final Long data1;
    private final Long data2;
    private final Long data3;
    private final Long data4;
    private final Long data5;
}
