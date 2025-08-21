package com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.mapper;

import com.github.nenidan.ne_ne_challenge.domain.admin.domain.model.StatisticDataModel;
import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.entity.StatisticData;

import java.time.YearMonth;

public class StatisticMapper {
    public static StatisticDataModel toDomain(StatisticData e) {
        return StatisticDataModel.of(
                e.getId(),
                e.getType(),
                e.getStatDate(),
                e.getPayload(),
                e.getCreatedAt()
        );
    }
}
