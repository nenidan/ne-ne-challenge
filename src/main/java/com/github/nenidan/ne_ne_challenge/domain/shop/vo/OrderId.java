package com.github.nenidan.ne_ne_challenge.domain.shop.vo;

import lombok.Getter;

@Getter
public class OrderId {

    private final Long value;

    public OrderId(Long value) {
        this.value = value;
    }
}
