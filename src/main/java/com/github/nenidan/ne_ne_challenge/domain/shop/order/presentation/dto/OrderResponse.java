package com.github.nenidan.ne_ne_challenge.domain.shop.order.presentation.dto;

import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.OrderStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderResponse {

    private final Long orderId;
    private final Long userId;
    private final OrderDetailDto orderDetail;
    private final OrderStatus status;
}
