package com.github.nenidan.ne_ne_challenge.domain.shop.stock.infrastructure;

import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.shop.exception.ShopErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.shop.exception.ShopException;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.Stock;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.StockRepository;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.infrastructure.entity.StockEntity;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StockRepositoryImpl implements StockRepository {

    private final StockJpaRepository stockJpaRepository;

    @Override
    public void createStock(ProductId productId) {
        StockEntity stockEntity = new StockEntity(productId.getValue());
        stockJpaRepository.save(stockEntity);
    }

    @Override
    public Stock increaseStock(ProductId productId, int quantity) {
        StockEntity stockEntity = stockJpaRepository.findByProductId(productId.getValue())
            .orElseThrow(() -> new ShopException(ShopErrorCode.PRODUCT_NOT_FOUND));

        stockEntity.increaseStock(quantity);
        return StockMapper.toDomain(stockEntity);
    }

    @Override
    public Stock decreaseStock(ProductId productId, int quantity) {
        StockEntity stockEntity = stockJpaRepository.findByProductId(productId.getValue())
            .orElseThrow(() -> new ShopException(ShopErrorCode.PRODUCT_NOT_FOUND));

        stockEntity.decreaseStock(quantity);
        return StockMapper.toDomain(stockEntity);
    }
}