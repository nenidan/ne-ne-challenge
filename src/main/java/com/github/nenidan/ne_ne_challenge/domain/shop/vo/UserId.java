package com.github.nenidan.ne_ne_challenge.domain.shop.vo;

import lombok.Getter;

@Getter
public class UserId {
    private final Long value;

    public UserId(Long value) {
        this.value = value;
    }
}
