package com.github.nenidan.ne_ne_challenge.domain.shop.order.infrastructure;

public interface OrderFlatProjection {
    Long getOrderId();
    Long getUserId();
    String getStatus();
    Long getProductId();
    String getProductName();
    String getProductDescription();
    Integer getPriceAtOrder();
}

