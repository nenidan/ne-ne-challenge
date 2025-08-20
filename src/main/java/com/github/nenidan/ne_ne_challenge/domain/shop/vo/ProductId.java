package com.github.nenidan.ne_ne_challenge.domain.shop.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductId {
    private Long value;

    public ProductId(Long value) {
        this.value = value;
    }
}
