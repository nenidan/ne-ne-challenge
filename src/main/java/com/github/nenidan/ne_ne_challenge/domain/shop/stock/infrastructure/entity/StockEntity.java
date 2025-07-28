package com.github.nenidan.ne_ne_challenge.domain.shop.stock.infrastructure.entity;

import com.github.nenidan.ne_ne_challenge.domain.shop.exception.ShopErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.shop.exception.ShopException;
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
}
