package com.github.nenidan.ne_ne_challenge.domain.shop.vo;

import lombok.Getter;

@Getter
public class StockId {
    private final Long value;

    public StockId(Long value) {
        this.value = value;
    }
}
