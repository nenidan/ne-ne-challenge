package com.github.nenidan.ne_ne_challenge.global.client.stock;

import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;

public interface StockRestClient {
    void decreaseStock(ProductId productId, int quantity);
    void decreaseReservedStock(ProductId productId, int quantity);
    void restoreStock(ProductId productId, int quantity);
    void restoreReservedStock(ProductId productId, int quantity);
}
