package com.github.nenidan.ne_ne_challenge.domain.shop.dto;

import com.github.nenidan.ne_ne_challenge.domain.shop.entity.OrderDetail;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderDetailDto {

    private final ProductDto product;
    private final Integer priceAtOrder;

    public static OrderDetailDto fromEntity(OrderDetail orderDetail) {
        return new OrderDetailDto(
            ProductDto.fromEntity(orderDetail.getProduct()),
            orderDetail.getPriceAtOrder()
        );
    }
}
