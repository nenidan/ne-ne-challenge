package com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.mapper;

import com.github.nenidan.ne_ne_challenge.domain.admin.domain.model.AopLogModel;
import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.entity.AopLog;


public class LogMapper {
    public static AopLogModel toDomain(AopLog entity) {
        return AopLogModel.of(
                entity.getId(),
                entity.getTargetId(),
                entity.getType(),
                entity.isSuccess(),
                entity.getMethod(),
                entity.getParams(),
                entity.getResult(),
                entity.getCreatedAt().toLocalDate() // LocalDateTime → LocalDate
        );
    }
}
