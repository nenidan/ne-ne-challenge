package com.github.nenidan.ne_ne_challenge.domain.shop.order.infrastructure.entity;

import com.github.nenidan.ne_ne_challenge.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class OrderDetailEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", unique = true)
    private OrderEntity orderEntity;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String productDescription;

    @Column(nullable = false)
    private Integer priceAtOrder;

    public OrderDetailEntity(Long productId, String productName, String productDescription, Integer priceAtOrder) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.priceAtOrder = priceAtOrder;
    }
}
