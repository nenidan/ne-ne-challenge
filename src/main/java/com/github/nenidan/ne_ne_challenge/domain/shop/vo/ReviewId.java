package com.github.nenidan.ne_ne_challenge.domain.shop.vo;

import lombok.Getter;

@Getter
public class ReviewId {
    private final Long value;

    public ReviewId(Long value) {
        this.value = value;
    }
}
