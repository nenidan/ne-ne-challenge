package com.github.nenidan.ne_ne_challenge.domain.admin.application.mapper;

import org.mapstruct.Mapper;

import com.github.nenidan.ne_ne_challenge.domain.admin.domain.model.StatisticDataModel;
import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.entity.StatisticData;

@Mapper(componentModel = "spring")
public interface StatisticDataMapper {
    StatisticDataModel toModel(StatisticData entity);
}
