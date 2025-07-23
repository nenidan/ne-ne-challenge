package com.github.nenidan.ne_ne_challenge.domain.shop.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.github.nenidan.ne_ne_challenge.domain.shop.dto.OrderDetailDto;
import com.github.nenidan.ne_ne_challenge.domain.shop.dto.OrderFlatProjection;
import com.github.nenidan.ne_ne_challenge.domain.shop.dto.ProductDto;
import com.github.nenidan.ne_ne_challenge.domain.shop.entity.Order;
import com.github.nenidan.ne_ne_challenge.domain.shop.type.OrderStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderResponse {

    private final Long orderId;
    private final Long userId;
    private final OrderDetailDto orderDetail;
    private final OrderStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;

    public static OrderResponse fromEntity(Order order) {
        return new OrderResponse(
            order.getId(),
            order.getUser().getId(),
            OrderDetailDto.fromEntity(order.getOrderDetail()),
            order.getStatus(),
            order.getCreatedAt(),
            order.getUpdatedAt(),
            order.getDeletedAt()
        );
    }
}
