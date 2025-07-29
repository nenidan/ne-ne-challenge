package com.github.nenidan.ne_ne_challenge.domain.shop.vo;

import lombok.Getter;

@Getter
public class ProductId {
    private final Long value;

    public ProductId(Long value) {
        this.value = value;
    }
}
