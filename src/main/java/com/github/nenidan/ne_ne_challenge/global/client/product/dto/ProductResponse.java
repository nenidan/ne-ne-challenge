package com.github.nenidan.ne_ne_challenge.global.client.product.dto;

import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto.ProductResult;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductResponse {

    private final Long id;
    private final String name;
    private final String description;
    private final Integer price;

    public static ProductResponse from(ProductResult productResult) {
        return new ProductResponse(
            productResult.getId().getValue(),
            productResult.getName(),
            productResult.getDescription(),
            productResult.getPrice()
        );
    }
}
