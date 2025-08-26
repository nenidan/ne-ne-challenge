package com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.vo;


import java.time.LocalDateTime;

import com.github.nenidan.ne_ne_challenge.domain.shop.vo.OrderDetailId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;

import lombok.Getter;

@Getter
public class OrderDetail {

    private final OrderDetailId orderDetailId;
    private final ProductId productId;
    private final String nameAtOrder;
    private final String descriptionAtOrder;
    private final int priceAtOrder;
    private final int quantity;
    private LocalDateTime deletedAt;

    public OrderDetail(OrderDetailId orderDetailId, ProductId productId, String nameAtOrder, String descriptionAtOrder, int priceAtOrder,
        int quantity) {
        this.orderDetailId = orderDetailId;
        this.productId = productId;
        this.nameAtOrder = nameAtOrder;
        this.descriptionAtOrder = descriptionAtOrder;
        this.priceAtOrder = priceAtOrder;
        this.quantity = quantity;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public void revertCancel() {
        this.deletedAt = null;
    }
}
