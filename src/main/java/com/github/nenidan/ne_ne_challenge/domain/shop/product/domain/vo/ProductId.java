package com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.vo;

import lombok.Getter;

@Getter
public class ProductId {
    private final Long productId;

    public ProductId(Long productId) {
        this.productId = productId;
    }
}
