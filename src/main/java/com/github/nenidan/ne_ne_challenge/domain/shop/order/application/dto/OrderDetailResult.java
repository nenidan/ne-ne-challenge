package com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto;


import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.vo.OrderDetail;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderDetailResult {

    private final Long productId;
    private final String productName;
    private final String productDescription;
    private final Integer priceAtOrder;
    private final int quantity;

    public static OrderDetailResult fromEntity(OrderDetail orderDetail) {
        return new OrderDetailResult(
            orderDetail.getProductId().getValue(),
            orderDetail.getNameAtOrder(),
            orderDetail.getDescriptionAtOrder(),
            orderDetail.getPriceAtOrder(),
            orderDetail.getQuantity()
        );
    }
}
