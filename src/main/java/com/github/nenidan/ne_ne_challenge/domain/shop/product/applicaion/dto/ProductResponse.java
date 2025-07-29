package com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto;

import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.Product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductResponse {

    private final Long id;
    private final String name;
    private final String description;
    private final Integer price;

    public static ProductResponse fromEntity(Product product) {
        return new ProductResponse(
            product.getProductId().getValue(),
            product.getProductName(),
            product.getProductDescription(),
            product.getProductPrice()
        );
    }
}
