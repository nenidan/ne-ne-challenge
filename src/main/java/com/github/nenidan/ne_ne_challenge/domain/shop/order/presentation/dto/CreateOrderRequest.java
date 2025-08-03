package com.github.nenidan.ne_ne_challenge.domain.shop.order.presentation.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateOrderRequest {

    private final Long productId;
    private final int quantity;
}
