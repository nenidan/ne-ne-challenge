package com.github.nenidan.ne_ne_challenge.domain.shop.stock.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
