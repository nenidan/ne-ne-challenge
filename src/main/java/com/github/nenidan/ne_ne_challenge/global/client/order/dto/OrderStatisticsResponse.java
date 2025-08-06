package com.github.nenidan.ne_ne_challenge.global.client.order.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderStatisticsResponse {

    private final Long orderId;
    private final Long userId;
    private final OrderDetailStatisticsDto product;
    private final String status;
}
