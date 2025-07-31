package com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.vo;

import lombok.Getter;

@Getter
public class OrderDetail {

    private final ProductId productId;
    private final String nameAtOrder;
    private final String descriptionAtOrder;
    private final int priceAtOrder;

    public OrderDetail(ProductId productId, String nameAtOrder, String descriptionAtOrder, int priceAtOrder) {
        this.productId = productId;
        this.nameAtOrder = nameAtOrder;
        this.descriptionAtOrder = descriptionAtOrder;
        this.priceAtOrder = priceAtOrder;
    }
}
