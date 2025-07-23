package com.github.nenidan.ne_ne_challenge.domain.shop.dto;

import java.time.LocalDateTime;

public interface OrderFlatProjection {
    Long getOrderId();
    Long getUserId();
    String getStatus();
    Integer getPriceAtOrder();
    Long getProductId();
    String getProductName();
    String getProductDescription();
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();
    LocalDateTime getDeletedAt();
}

