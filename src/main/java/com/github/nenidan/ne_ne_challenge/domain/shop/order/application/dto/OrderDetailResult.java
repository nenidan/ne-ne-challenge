package com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.vo.OrderDetail;

import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class OrderDetailResult {

    private final Long productId;
    private final String productName;
    private final String productDescription;
    private final Integer priceAtOrder;
    private final int quantity;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public OrderDetailResult(
            @JsonProperty("productId") Long productId,
            @JsonProperty("productName") String productName,
            @JsonProperty("productDescription") String productDescription,
            @JsonProperty("priceAtOrder") Integer priceAtOrder,
            @JsonProperty("quantity") int quantity
    ){
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.priceAtOrder = priceAtOrder;
        this.quantity = quantity;
    }

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
