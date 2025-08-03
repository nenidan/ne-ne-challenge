package com.github.nenidan.ne_ne_challenge.domain.shop.stock.infrastructure.mapper;

import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.model.Stock;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.infrastructure.entity.StockEntity;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.StockId;

public class StockMapper {

    public static Stock toDomain(StockEntity stockEntity) {
        return new Stock (
            new StockId(stockEntity.getId()),
            new ProductId(stockEntity.getProductId()),
            stockEntity.getQuantity(),
            stockEntity.getDeletedAt()
        );
    }

    public static StockEntity toEntity(Stock stock) {
        return new StockEntity(
            stock.getStockId().getValue(),
            stock.getProductId().getValue(),
            stock.getQuantity(),
            stock.getDeletedAt()
        );
    }
}