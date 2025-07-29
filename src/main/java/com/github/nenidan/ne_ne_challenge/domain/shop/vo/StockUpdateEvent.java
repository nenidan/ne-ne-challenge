package com.github.nenidan.ne_ne_challenge.domain.shop.vo;

import lombok.Getter;

@Getter
public class StockUpdateEvent {

    private final ProductId productId;
    private final int quantity;

    public StockUpdateEvent(ProductId productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
