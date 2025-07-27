package com.github.nenidan.ne_ne_challenge.domain.point.presentation.dto.response;

import com.github.nenidan.ne_ne_challenge.domain.point.domain.PointTransaction;
import com.github.nenidan.ne_ne_challenge.domain.point.domain.type.PointReason;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
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
