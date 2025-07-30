package com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain;

import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;

public interface StockRepository {
    void save(ProductId productId);
    Stock increase(ProductId productId, int quantity);
    void decrease(ProductId productId, int quantity);
    Stock findById(ProductId productId);
    void delete(ProductId productId);
}