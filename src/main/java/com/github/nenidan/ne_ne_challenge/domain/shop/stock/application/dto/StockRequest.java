package com.github.nenidan.ne_ne_challenge.domain.shop.stock.application.dto;

import lombok.Getter;

@Getter
public class StockRequest {
    private final int quantity;

    public StockRequest(int quantity) {
        this.quantity = quantity;
    }
}
