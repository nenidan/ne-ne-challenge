package com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.vo;

import com.github.nenidan.ne_ne_challenge.domain.shop.exception.ShopErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.shop.exception.ShopException;

import lombok.Getter;

@Getter
public class ProductId {
    private final Long value;

    public ProductId(Long value) {
        if (value == null || value <= 0) {
            throw new ShopException(ShopErrorCode.PRODUCT_NOT_FOUND);
        }
        this.value = value;
    }
}
