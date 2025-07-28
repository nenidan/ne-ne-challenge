package com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain;

import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;

public interface StockRepository {
    void createStock(ProductId productId);
    Stock increaseStock(ProductId productId, int quantity);
    Stock decreaseStock(ProductId productId, int quantity);
}