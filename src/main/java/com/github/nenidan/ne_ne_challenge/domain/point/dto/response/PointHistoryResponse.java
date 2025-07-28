package com.github.nenidan.ne_ne_challenge.domain.point.dto.response;

import com.github.nenidan.ne_ne_challenge.domain.point.entity.PointTransaction;
import com.github.nenidan.ne_ne_challenge.domain.point.type.PointReason;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PointHistoryResponse {

    private Long pointTransactionId;

    private int amount;

    private PointReason reason;

    private String description;

    public static PointHistoryResponse from(PointTransaction pointTransaction) {
        return new PointHistoryResponse(
            pointTransaction.getId(),
            pointTransaction.getAmount(),
            pointTransaction.getReason(),
            pointTransaction.getDescription()
        );
    }
}
