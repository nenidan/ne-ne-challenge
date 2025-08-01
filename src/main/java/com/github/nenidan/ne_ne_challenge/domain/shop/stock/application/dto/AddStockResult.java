package com.github.nenidan.ne_ne_challenge.domain.shop.stock.application.dto;

import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.model.Stock;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AddStockResult {

    private final Long StockId;
    private final Long ProductId;
    private final int quantity;

    public static AddStockResult from(Stock stock){
        return new AddStockResult(
            stock.getStockId().getValue(),
            stock.getProductId().getValue(),
            stock.getQuantity()
        );
    }
}
