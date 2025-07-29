package com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.vo;

import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;

import lombok.Getter;

@Getter
public class OrderDetail {

    private final ProductId productId;
    private final String nameAtOrder;
    private final String descriptionAtOrder;
    private final int priceAtOrder;
    private final int quantity;

    public OrderDetail(ProductId productId, String nameAtOrder, String descriptionAtOrder, int priceAtOrder,
        int quantity) {
        this.productId = productId;
        this.nameAtOrder = nameAtOrder;
        this.descriptionAtOrder = descriptionAtOrder;
        this.priceAtOrder = priceAtOrder;
        this.quantity = quantity;
    }
}
