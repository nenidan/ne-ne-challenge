package com.github.nenidan.ne_ne_challenge.domain.shop.order.presentation.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderDetailDto {

    private final Long productId;
    private final String productName;
    private final String productDescription;
    private final Integer priceAtOrder;
    private final int quantity;
}
