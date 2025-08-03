package com.github.nenidan.ne_ne_challenge.global.client.point.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PointChargeRequest {
    private int amount;
    private String reason;
    private String orderId;
}
