package com.github.nenidan.ne_ne_challenge.domain.point.application.dto.response;

import com.github.nenidan.ne_ne_challenge.domain.point.domain.type.PointReason;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PointHistoryResult {

    private Long pointTransactionId;

    private int amount;

    private PointReason reason;

    private String description;
}
