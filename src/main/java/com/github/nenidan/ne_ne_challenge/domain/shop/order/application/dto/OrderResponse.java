package com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto;

import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.Order;
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

    public static OrderResponse fromEntity(Order order) {
        return new OrderResponse(
            order.getOrderId().getValue(),
            order.getUserid().getValue(),
            OrderDetailDto.fromEntity(order.getOrderDetail()),
            order.getOrderStatus()
        );
    }
}
