package com.github.nenidan.ne_ne_challenge.domain.shop.stock.infrastructure.entity;

import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.exception.StockErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.exception.StockException;
import com.github.nenidan.ne_ne_challenge.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "stock")
@NoArgsConstructor
public class StockEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private int quantity;

    public StockEntity(Long productId) {
        this.productId = productId;
        this.quantity = 0;
    }

    public StockEntity(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
    public void increaseStock(int quantity) {
        this.quantity += quantity;
    }


    public void decreaseStock(int quantity) {
        if(quantity <= 0) {
            throw new StockException(StockErrorCode.INVALID_DECREASE_QUANTITY);
        }
        if(this.quantity < quantity) {
            throw new StockException(StockErrorCode.OUT_OF_STOCK);
        }
        this.quantity -= quantity;
    }
}
