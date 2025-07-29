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
        stockRepository.save(new ProductId(productId));
    }

    @Transactional
    public StockResponse increaseStock(Long productId, int quantity) {
        Stock stock = stockRepository.increase(new ProductId(productId), quantity);
        return StockResponse.from(stock);
    }

    @Transactional
    public void decreaseStock(Long productId, int quantity) {
        stockRepository.decrease(new ProductId(productId), quantity);
    }

    @Transactional(readOnly = true)
    public StockResponse getStock(Long productId) {
        Stock stock = stockRepository.findById(new ProductId(productId));
        return  StockResponse.from(stock);
    }
}