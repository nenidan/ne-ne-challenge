package com.github.nenidan.ne_ne_challenge.domain.shop.stock.presentation.dto;

import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.Stock;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AddStockResponse {

    private final Long StockId;
    private final Long ProductId;
    private final int quantity;

    public static AddStockResponse from(Stock stock){
        return new AddStockResponse(
            stock.getStockId().getValue(),
            stock.getProductId().getValue(),
            stock.getQuantity()
        );
    }
}
