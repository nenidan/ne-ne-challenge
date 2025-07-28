package com.github.nenidan.ne_ne_challenge.domain.shop.stock.infrastructure;

import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.Stock;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.infrastructure.entity.StockEntity;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.StockId;

public class StockMapper {

    public static Stock toDomain(StockEntity stockEntity) {
        return new Stock (
            new StockId(stockEntity.getId()),
            new ProductId(stockEntity.getProductId()),
            stockEntity.getQuantity()
        );
    }

    public static StockEntity toEntity(Stock stock) {
        return new StockEntity(
            stock.getProductId().getValue(),
            stock.getQuantity()
        );
    }
}