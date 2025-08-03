package com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.repository;

import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.model.Stock;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;

public interface StockRepository {
    void save(ProductId productId);
    Stock save(Stock stock);
    Stock findById(ProductId productId);
}