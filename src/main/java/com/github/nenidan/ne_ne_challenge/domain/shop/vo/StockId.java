package com.github.nenidan.ne_ne_challenge.domain.shop.vo;

import com.github.nenidan.ne_ne_challenge.domain.shop.exception.ShopErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.shop.exception.ShopException;

import lombok.Getter;

@Getter
public class StockId {
    private final Long value;

    public StockId(Long value) {
        if (value < 0) {
            throw new ShopException(ShopErrorCode.STOCK_NOT_FOUND);
        }
        this.value = value;
    }
}
