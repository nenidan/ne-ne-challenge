package com.github.nenidan.ne_ne_challenge.global.client.point.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class PointChargeRequest {
    private int amount;
    private String reason;
    private String orderId;
}
