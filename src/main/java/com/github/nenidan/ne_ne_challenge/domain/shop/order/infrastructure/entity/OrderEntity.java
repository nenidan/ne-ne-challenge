package com.github.nenidan.ne_ne_challenge.domain.shop.order.infrastructure.entity;

import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.type.OrderStatus;
import com.github.nenidan.ne_ne_challenge.global.entity.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class OrderEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @OneToOne(mappedBy = "orderEntity", cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_detail_id")
    private OrderDetailEntity orderDetailEntity;

    @Setter
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.CONFIRM;

    public OrderEntity(Long userId, OrderDetailEntity orderDetailEntity) {
        this.userId = userId;
        setOrderDetailEntity(orderDetailEntity);
    }

    public void setOrderDetailEntity(OrderDetailEntity orderDetailEntity) {
        this.orderDetailEntity = orderDetailEntity;
        orderDetailEntity.setOrderEntity(this);
    }

    @Override
    public void delete() {
        setStatus(OrderStatus.CANCELED);

        super.delete();
        orderDetailEntity.delete();
    }
}
