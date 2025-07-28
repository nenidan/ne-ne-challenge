package com.github.nenidan.ne_ne_challenge.domain.shop.stock.infrastructure;

import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.StockRepository;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.infrastructure.entity.StockEntity;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class StockRepositoryImpl implements StockRepository {

    private final StockJpaRepository stockJpaRepository;

    @Override
    public void createStock(ProductId productId) {
        StockEntity stockEntity = new StockEntity(productId.getValue());
        stockJpaRepository.save(stockEntity);
    }
}
