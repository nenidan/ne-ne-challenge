package com.github.nenidan.ne_ne_challenge.domain.shop.order.infrastructure.mapper;

import java.time.LocalDateTime;

public interface OrderFlatProjection {
    Long getOrderId();
    Long getUserId();
    String getStatus();
    Long getOrderDetailId();
    Long getProductId();
    String getProductName();
    String getProductDescription();
    int getPriceAtOrder();
    int getQuantity();
    LocalDateTime getDeletedAt();
}

