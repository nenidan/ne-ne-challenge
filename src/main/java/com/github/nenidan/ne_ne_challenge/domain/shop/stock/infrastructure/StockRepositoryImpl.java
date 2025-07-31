package com.github.nenidan.ne_ne_challenge.domain.shop.stock.infrastructure;

import org.springframework.stereotype.Repository;


import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.Stock;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.StockRepository;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.exception.StockErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.exception.StockException;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.infrastructure.entity.StockEntity;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StockRepositoryImpl implements StockRepository {

    private final StockJpaRepository stockJpaRepository;

    @Override
    public void save(ProductId productId) {
        StockEntity stockEntity = new StockEntity(productId.getValue());
        stockJpaRepository.save(stockEntity);
    }

    @Override
    public Stock increase(ProductId productId, int quantity) {
        StockEntity stockEntity = stockJpaRepository.findByProductId(productId.getValue())
            .orElseThrow(() -> new StockException(StockErrorCode.STOCK_NOT_FOUND));

        stockEntity.increaseStock(quantity);
        return StockMapper.toDomain(stockEntity);
    }

    @Override
    public void decrease(ProductId productId, int quantity) {
        StockEntity stockEntity = stockJpaRepository.findByProductId(productId.getValue())
            .orElseThrow(() -> new StockException(StockErrorCode.STOCK_NOT_FOUND));

        stockEntity.decreaseStock(quantity);
        StockMapper.toDomain(stockEntity);
    }

    @Override
    public Stock findById(ProductId productId) {
        StockEntity stockEntity = stockJpaRepository.findByProductId(productId.getValue())
            .orElseThrow(() -> new StockException(StockErrorCode.STOCK_NOT_FOUND));

        return StockMapper.toDomain(stockEntity);
    }

    @Override
    public void delete(ProductId productId) {
        StockEntity stockEntity = stockJpaRepository.findByProductId(productId.getValue())
            .orElseThrow(() -> new StockException(StockErrorCode.STOCK_NOT_FOUND));


        stockEntity.delete();
    }
}