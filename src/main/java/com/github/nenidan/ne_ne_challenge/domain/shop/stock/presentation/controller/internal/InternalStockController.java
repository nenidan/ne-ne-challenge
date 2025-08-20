package com.github.nenidan.ne_ne_challenge.domain.shop.stock.presentation.controller.internal;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.shop.stock.application.dto.AddStockCommand;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.application.service.StockService;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class InternalStockController {

    private final StockService stockService;

    // 재고 감소 (확정)
    @PatchMapping("/internal/products/{productId}/stocks/confirm")
    public void decreaseStock(
        @PathVariable Long productId,
        @RequestBody AddStockCommand addStockCommand
    ){
        stockService.decreaseStock(new ProductId(productId), addStockCommand);
    }

    // 예비 재고 감소
    @PatchMapping("/internal/products/{id}/stocks")
    public void decreaseReservedStock(
        @PathVariable Long id,
        @RequestBody AddStockCommand addStockCommand
    ){
        stockService.decreaseReservedStock(new ProductId(id),addStockCommand);
    }


    // 재고 복구
    @PatchMapping("/internal/stocks/{productId}/confirm")
    public void restoreStock(
        @PathVariable Long productId,
        @RequestBody AddStockCommand addStockCommand
    ){
        stockService.restoreStock(new ProductId(productId), addStockCommand);
    }

    // 예비 재고 복구
    @PatchMapping("/internal/stocks/{productId}")
    public void restoreReservedStock(
        @PathVariable Long productId,
        @RequestBody AddStockCommand addStockCommand
    ){
        stockService.restoreReservedStock(new ProductId(productId), addStockCommand);
    }

    // 예비 재고 복구 취소
    @DeleteMapping("/internal/stocks/{productId}")
    public void cancelRestoredReservedStock(
        @PathVariable Long productId,
        @RequestBody AddStockCommand addStockCommand
    ){
        stockService.canceledRestoreReservedStock(new ProductId(productId), addStockCommand);
    }
}
