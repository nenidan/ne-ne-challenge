package com.github.nenidan.ne_ne_challenge.domain.shop.order.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {

    @Schema(description = "상품 식별자", example = "1")
    private Long productId;
    @Schema(description = "주문 수량", example = "2")
    private int quantity;
}
