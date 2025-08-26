package com.github.nenidan.ne_ne_challenge.domain.point.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PointChargeRequest {

    private int amount;            // 충전할 포인트 금액
    private String reason;    // 충전 사유
    private String orderId;
}