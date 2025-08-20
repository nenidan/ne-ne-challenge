package com.github.nenidan.ne_ne_challenge.domain.shop.order.presentation.dto;

import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.type.OrderStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderResponse {

    @Schema(description = "주문 식별자", example = "1")
    private final Long orderId;
    @Schema(description = "사용자 식별자", example = "1")
    private final Long userId;
    @Schema(description = "상품 상세")
    private final OrderDetailDto orderDetail;
    @Schema(description = "주문 상태", example = "CONFIRM")
    private final OrderStatus status;
}
