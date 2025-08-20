package com.github.nenidan.ne_ne_challenge.domain.shop.order.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {

    private Long productId;
    private int quantity;
}
