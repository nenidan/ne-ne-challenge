package com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto;

import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.model.Product;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductStatisticsResult {

    private final ProductId id;
    private final String name;
    private final String description;
    private final Integer price;

    public static ProductStatisticsResult fromEntity(Product product) {
        return new ProductStatisticsResult(
            product.getProductId(),
            product.getProductName(),
            product.getProductDescription(),
            product.getProductPrice()
        );
    }
}
