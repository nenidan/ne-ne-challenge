package com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.model.Order;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.type.OrderStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class OrderResult {

    private final Long orderId;
    private final Long userId;
    private final OrderDetailResult orderDetail;
    private final OrderStatus status;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public OrderResult(
            @JsonProperty("orderId") Long orderId,
            @JsonProperty("userId") Long userId,
            @JsonProperty("orderDetail") OrderDetailResult orderDetail,
            @JsonProperty("status") OrderStatus status
    ){
        this.orderId = orderId;
        this.userId = userId;
        this.orderDetail = orderDetail;
        this.status = status;
    }

    public static OrderResult fromEntity(Order order) {
        return new OrderResult(
            order.getOrderId().getValue(),
            order.getUserid().getValue(),
            OrderDetailResult.fromEntity(order.getOrderDetail()),
            order.getOrderStatus()
        );
    }
}
