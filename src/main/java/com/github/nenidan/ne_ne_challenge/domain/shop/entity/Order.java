package com.github.nenidan.ne_ne_challenge.domain.shop.entity;

import java.util.ArrayList;
import java.util.List;

import com.github.nenidan.ne_ne_challenge.domain.shop.type.OrderStatus;
import com.github.nenidan.ne_ne_challenge.domain.user.entity.User;
import com.github.nenidan.ne_ne_challenge.global.entity.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private final List<OrderDetail> orderDetails = new ArrayList<>();

    @Setter
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    public Order(User user) {
        this.user = user;
    }

    public void addOrderDetail(OrderDetail orderDetail) {
        this.orderDetails.add(orderDetail);
        orderDetail.setOrder(this);
    }

    @Override
    public void delete() {
        setStatus(OrderStatus.CANCELED);

        super.delete();
        for (OrderDetail orderDetail : orderDetails) {
            orderDetail.delete();
        }
    }
}
