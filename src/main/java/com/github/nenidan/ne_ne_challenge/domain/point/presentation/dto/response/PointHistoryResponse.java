package com.github.nenidan.ne_ne_challenge.domain.point.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PointHistoryResponse {

    private Long pointTransactionId;

    private int amount;

    private String reason;

    private String description;
}
