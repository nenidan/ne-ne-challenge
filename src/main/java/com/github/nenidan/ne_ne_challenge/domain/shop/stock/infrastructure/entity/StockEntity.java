package com.github.nenidan.ne_ne_challenge.domain.shop.stock.infrastructure.entity;

import java.time.LocalDateTime;

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
    private Integer reservedQuantity;

    @Column(nullable = false)
    private Integer quantity;

    public StockEntity(Long id, Long productId, Integer reservedQuantity, Integer quantity, LocalDateTime deletedAt) {
        this.id = id;
        this.productId = productId;
        this.reservedQuantity = reservedQuantity;
        this.quantity = quantity;
        this.deletedAt = deletedAt;
    }
}
