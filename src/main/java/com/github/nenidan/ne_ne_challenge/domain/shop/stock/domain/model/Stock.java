package com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.model;

import java.time.LocalDateTime;

import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.exception.StockErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.exception.StockException;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.StockId;

public class Stock {

    private final StockId stockId;
    private final ProductId productId;
    private Integer quantity;
    private LocalDateTime deletedAt;

    public Stock(StockId stockId, ProductId productId, Integer quantity, LocalDateTime deletedAt) {
        this.stockId = stockId;
        this.productId = productId;
        this.quantity = quantity;
        this.deletedAt = deletedAt;
    }

    public StockId getStockId() {
        return stockId;
    }

    public ProductId getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void inbound(int quantity) {
        if(quantity <= 0){
            throw new StockException(StockErrorCode.INVALID_INBOUND_QUANTITY);
        }
        this.quantity += quantity;
    }

    // 관리자 전용 : 출고
    public void outbound(int quantity) {
        if(quantity <= 0){
            throw new StockException(StockErrorCode.INVALID_INBOUND_QUANTITY);
        }
        if(this.quantity < quantity){
            throw new StockException(StockErrorCode.NOT_ENOUGH_OUTBOUND_QUANTITY);
        }
        this.quantity -= quantity;
    }

    // 소비자 전용 : 재고 감소
    public void decreaseQuantity(Integer quantity) {
        if(quantity <= 0){
            throw new StockException(StockErrorCode.INVALID_DECREASE_QUANTITY);
        }
        if(this.quantity < quantity){
            throw new StockException(StockErrorCode.NOT_ENOUGH_OUTBOUND_QUANTITY);
        }
        this.quantity -= quantity;
    }

    // 재고 복구
    public void restoreQuantity(Integer quantity) {
        if(quantity <= 0){
            throw new StockException(StockErrorCode.INVALID_RESTORE_QUANTITY);
        }
        this.quantity += quantity;
    }

    // 재고 복구 취소 (보상 트랜잭션)
    public void convertRestoreQuantity(Integer quantity) {
        this.quantity -= quantity;
    }

    public void checkDeletableOnlyIfStockEmpty() {
        if (quantity > 0) {
            throw new StockException(StockErrorCode.STOCK_NOT_EMPTY);
        }
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }
}
