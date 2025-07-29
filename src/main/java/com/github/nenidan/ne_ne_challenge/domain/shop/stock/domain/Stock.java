package com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain;

import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.StockId;

public class Stock {

    private final StockId stockId;
    private final ProductId productId;
    private final int quantity;

    public Stock(StockId stockId, ProductId productId, int quantity) {
        this.stockId = stockId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public StockId getStockId() {
        return stockId;
    }

    public ProductId getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
