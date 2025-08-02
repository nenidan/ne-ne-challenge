package com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto;

import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.vo.ProductId;

import lombok.Getter;

@Getter
public class OrderedProduct {

    private final ProductId productId;
    private final String productName;
    private final String productDescription;
    private final Integer priceAtOrder;

    public OrderedProduct(ProductId productId, String productName, String productDescription, Integer priceAtOrder) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.priceAtOrder = priceAtOrder;
    }
}
