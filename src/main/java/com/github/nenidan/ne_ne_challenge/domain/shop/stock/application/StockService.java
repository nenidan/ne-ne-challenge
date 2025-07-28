package com.github.nenidan.ne_ne_challenge.domain.shop.stock.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.nenidan.ne_ne_challenge.domain.shop.stock.application.dto.StockResponse;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.Stock;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.StockRepository;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    @Transactional
    public void createStock(Long productId) {
        stockRepository.createStock(new ProductId(productId));
    }

    @Transactional
    public StockResponse increaseStock(Long productId, int quantity) {
        Stock stock = stockRepository.increaseStock(new ProductId(productId), quantity);
        return StockResponse.from(stock);
    }

    @Transactional
    public StockResponse decreaseStock(Long productId, int quantity) {
        Stock stock = stockRepository.decreaseStock(new ProductId(productId), quantity);
        return  StockResponse.from(stock);
    }
}