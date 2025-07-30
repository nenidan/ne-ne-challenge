package com.github.nenidan.ne_ne_challenge.domain.shop.dto;

import com.github.nenidan.ne_ne_challenge.domain.shop.entity.Product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductDto {

    private final Long productId;
    private final String productName;
    private final String productDescription;

    public static ProductDto fromEntity(Product product) {
        return new ProductDto(
            product.getId(),
            product.getProductName(),
            product.getProductDescription()
        );
    }
}
