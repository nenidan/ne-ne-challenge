package com.github.nenidan.ne_ne_challenge.domain.shop.stock.infrastructure.repository;

import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.exception.StockErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.exception.StockException;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.model.Stock;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.repository.StockRepository;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.infrastructure.entity.StockEntity;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.infrastructure.mapper.StockMapper;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StockRepositoryImpl implements StockRepository {

    private final StockJpaRepository stockJpaRepository;

    @Override
    public void save(ProductId productId) {
        StockEntity stockEntity = new StockEntity(
            null,
            productId.getValue(),
            0,
            0,
            null
        );
        stockJpaRepository.save(stockEntity);
    }

    @Override
    public Stock save(Stock stock) {
        StockEntity entity = StockMapper.toEntity(stock);
        StockEntity save = stockJpaRepository.save(entity);
        return StockMapper.toDomain(save);
    }

    @Override
    public Stock findById(ProductId productId) {
        StockEntity stockEntity = stockJpaRepository.findByProductId(productId.getValue())
            .orElseThrow(() -> new StockException(StockErrorCode.STOCK_NOT_FOUND));

        return StockMapper.toDomain(stockEntity);
    }
}