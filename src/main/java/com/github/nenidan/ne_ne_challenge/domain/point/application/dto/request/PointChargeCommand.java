package com.github.nenidan.ne_ne_challenge.domain.point.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PointChargeCommand {

    private int amount;            // 충전할 포인트 금액
    private String reason;    // 충전 사유 (예: "결제", "관리자 지급", "이벤트 지급" 등)
    private String orderId;
}
