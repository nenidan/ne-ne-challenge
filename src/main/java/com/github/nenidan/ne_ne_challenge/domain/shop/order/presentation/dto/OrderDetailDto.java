package com.github.nenidan.ne_ne_challenge.domain.shop.order.presentation.dto;


import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.vo.OrderDetail;

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
