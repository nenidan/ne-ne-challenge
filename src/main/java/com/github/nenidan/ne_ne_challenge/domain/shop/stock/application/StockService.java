package com.github.nenidan.ne_ne_challenge.domain.shop.stock.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.nenidan.ne_ne_challenge.domain.shop.stock.application.dto.AddStockCommand;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.application.dto.AddStockResult;
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
    public AddStockResult increaseStock(AddStockCommand addStockCommand) {
        Stock stock = stockRepository.increase(addStockCommand.getProductId(), addStockCommand.getQuantity());
        return AddStockResult.from(stock);
    }

    @Transactional
    public void decreaseStock(AddStockCommand addStockCommand) {
        stockRepository.decrease(addStockCommand.getProductId(), addStockCommand.getQuantity());
    }

    @Transactional(readOnly = true)
    public AddStockResult getStock(Long productId) {
        Stock stock = stockRepository.findById(new ProductId(productId));
        return  AddStockResult.from(stock);
    }
}