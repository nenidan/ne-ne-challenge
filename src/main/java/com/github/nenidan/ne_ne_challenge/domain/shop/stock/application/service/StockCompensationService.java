package com.github.nenidan.ne_ne_challenge.domain.shop.stock.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.model.Stock;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.repository.StockRepository;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockCompensationService {

    private final StockRepository stockRepository;

    @Transactional
    public void compensateRestoreStock(Long productId, Integer quantity) {
        Stock stock = stockRepository.findById(new ProductId(productId));
        stock.convertRestoreQuantity(quantity);
        stockRepository.save(stock);
    }
}
