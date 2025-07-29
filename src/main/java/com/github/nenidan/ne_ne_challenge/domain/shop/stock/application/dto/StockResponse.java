package com.github.nenidan.ne_ne_challenge.domain.shop.stock.application.dto;

import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.Stock;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StockResponse {

    private final Long StockId;
    private final Long ProductId;
    private final int quantity;

    public static StockResponse from(Stock stock){
        return new StockResponse(
            stock.getStockId().getValue(),
            stock.getProductId().getValue(),
            stock.getQuantity()
        );
    }
}
