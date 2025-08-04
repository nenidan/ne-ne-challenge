package com.github.nenidan.ne_ne_challenge.domain.admin.application.mapper;

import com.github.nenidan.ne_ne_challenge.domain.admin.domain.model.StatisticDataModel;
import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.entity.StatisticData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatisticDataMapper {
    StatisticDataModel toModel(StatisticData entity);
}
