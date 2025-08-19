package com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto.OrderDetailResult;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.type.OrderStatus;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.model.Product;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductResult {

    private final ProductId id;
    private final String name;
    private final String description;
    private final Integer price;

    public static ProductResult fromEntity(Product product) {
        return new ProductResult(
            product.getProductId(),
            product.getProductName(),
            product.getProductDescription(),
            product.getProductPrice()
        );
    }
}
