package com.github.nenidan.ne_ne_challenge.domain.shop.order.presentation.dto;

import com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto.OrderStatisticsResult;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.type.OrderStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderStatisticsResponse {

    private final Long orderId;
    private final Long userId;
    private final OrderDetailStatisticsDto product;
    private final OrderStatus status;

    public static OrderStatisticsResponse from(OrderStatisticsResult orderStatisticsResult){
        return new OrderStatisticsResponse(
            orderStatisticsResult.getOrderId(),
            orderStatisticsResult.getUserId(),
            new OrderDetailStatisticsDto(
                orderStatisticsResult.getOrderDetail().getProductId(),
                orderStatisticsResult.getOrderDetail().getProductName(),
                orderStatisticsResult.getOrderDetail().getProductDescription(),
                orderStatisticsResult.getOrderDetail().getPriceAtOrder(),
                orderStatisticsResult.getOrderDetail().getQuantity()
            ),
            orderStatisticsResult.getStatus()
        );
    }
}
