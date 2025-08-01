package com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto;

import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.model.Order;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.type.OrderStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderResult {

    private final Long orderId;
    private final Long userId;
    private final OrderDetailResult orderDetail;
    private final OrderStatus status;

    public static OrderResult fromEntity(Order order) {
        return new OrderResult(
            order.getOrderId().getValue(),
            order.getUserid().getValue(),
            OrderDetailResult.fromEntity(order.getOrderDetail()),
            order.getOrderStatus()
        );
    }
}
