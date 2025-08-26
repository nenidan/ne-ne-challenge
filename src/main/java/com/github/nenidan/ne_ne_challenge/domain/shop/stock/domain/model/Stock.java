package com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.model;

import java.time.LocalDateTime;

import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.exception.StockErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.exception.StockException;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.StockId;

public class Stock {

    private final StockId stockId;
    private final ProductId productId;
    private Integer reservedQuantity;
    private Integer quantity;
    private LocalDateTime deletedAt;

    public Stock(StockId stockId, ProductId productId, Integer reservedQuantity, Integer quantity, LocalDateTime deletedAt) {
        this.stockId = stockId;
        this.productId = productId;
        this.reservedQuantity = reservedQuantity;
        this.quantity = quantity;
        this.deletedAt = deletedAt;
    }

    public StockId getStockId() {
        return stockId;
    }

    public ProductId getProductId() {
        return productId;
    }

    public int getReservedQuantity() {return reservedQuantity;}

    public int getQuantity() {
        return quantity;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    // 관리자 전용 : 입고
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
        if(this.reservedQuantity < quantity){
            throw new StockException(StockErrorCode.NOT_ENOUGH_OUTBOUND_QUANTITY);
        }
        this.reservedQuantity -= quantity;
    }

    // 소비자 전용 : 재고 출고 시, 예비 재고 저장
    public void decreaseReservedQuantity(Integer quantity) {
        if(quantity <= 0){
            throw new StockException(StockErrorCode.INVALID_DECREASE_QUANTITY);
        }
        if(this.quantity < quantity){
            throw new StockException(StockErrorCode.NOT_ENOUGH_DECREASE_QUANTITY);
        }
        this.quantity -= quantity;
        this.reservedQuantity += quantity;
    }

    // 소비자 전용 : 재고 복구 (확정)
    public void restoreQuantity(Integer quantity) {
        if(quantity <= 0){
            throw new StockException(StockErrorCode.INVALID_RESTORE_QUANTITY);
        }
        this.reservedQuantity -= quantity;
        this.quantity += quantity;
    }

    // 소비자 전용 : 예비 재고 복구
    public void restoreReservedQuantity(Integer quantity) {
        if(quantity <= 0){
            throw new StockException(StockErrorCode.INVALID_RESTORE_QUANTITY);
        }
        this.reservedQuantity += quantity;
    }

    // 소비자 전용 : 예비 재고 복구 취소
    public void canceledRestoreReservedQuantity(Integer quantity) {
        if(quantity <= 0){
            throw new StockException(StockErrorCode.INVALID_RESTORE_QUANTITY);
        }
        this.reservedQuantity -= quantity;
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
