package com.github.nenidan.ne_ne_challenge.domain.shop.order.presentation.dto;

import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateOrderRequest {

    private final ProductId productId;
    private final int quantity;
}
