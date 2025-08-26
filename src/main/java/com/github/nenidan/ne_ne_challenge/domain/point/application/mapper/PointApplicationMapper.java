package com.github.nenidan.ne_ne_challenge.domain.point.application.mapper;

import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.response.PointHistoryResult;
import com.github.nenidan.ne_ne_challenge.domain.point.domain.model.PointTransaction;

public class PointApplicationMapper {

    public static PointHistoryResult toPointHistoryResult(PointTransaction transaction) {
        return new PointHistoryResult(
            transaction.getId(),
            transaction.getAmount(),
            transaction.getReason(),
            transaction.getDescription()
        );
    }
}
