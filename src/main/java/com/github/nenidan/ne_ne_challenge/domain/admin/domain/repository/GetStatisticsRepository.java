package com.github.nenidan.ne_ne_challenge.domain.admin.domain.repository;

import com.github.nenidan.ne_ne_challenge.domain.admin.domain.model.StatisticDataModel;
import com.github.nenidan.ne_ne_challenge.domain.admin.domain.type.DomainType;

import java.time.YearMonth;
import java.util.Optional;

public interface GetStatisticsRepository{
    Optional<StatisticDataModel> findMonthlyOne(DomainType domainType, YearMonth month);
}
