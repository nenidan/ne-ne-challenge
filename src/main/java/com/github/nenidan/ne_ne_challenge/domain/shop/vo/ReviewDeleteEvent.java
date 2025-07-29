package com.github.nenidan.ne_ne_challenge.domain.shop.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReviewDeleteEvent {
    private final ProductId productId;
}
