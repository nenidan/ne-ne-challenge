package com.github.nenidan.ne_ne_challenge.domain.admin.application.mapper;

import com.github.nenidan.ne_ne_challenge.domain.admin.domain.model.AopLogModel;
import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.entity.AopLog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AopLogMapper {
    AopLogModel toModel(AopLog entity);
}
