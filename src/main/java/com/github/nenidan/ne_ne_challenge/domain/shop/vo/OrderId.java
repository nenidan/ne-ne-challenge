package com.github.nenidan.ne_ne_challenge.domain.shop.vo;

import com.github.nenidan.ne_ne_challenge.domain.shop.exception.ShopErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.shop.exception.ShopException;

import lombok.Getter;

@Getter
public class OrderId {

    private final Long value;

    public OrderId(Long value) {
        if (value == null || value <= 0) {
            throw new ShopException(ShopErrorCode.ORDER_NOT_FOUND);
        }
        this.value = value;
    }
}
