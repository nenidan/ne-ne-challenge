package com.github.nenidan.ne_ne_challenge.domain.shop.product.presentation.dto;

import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto.ProductStatisticsResult;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductStatisticsResponse {

    private final Long id;
    private final String name;
    private final String description;
    private final Integer price;

    public static ProductStatisticsResponse from(ProductStatisticsResult productStatisticsResult) {
        return new ProductStatisticsResponse(
            productStatisticsResult.getId().getValue(),
            productStatisticsResult.getName(),
            productStatisticsResult.getDescription(),
            productStatisticsResult.getPrice()
        );
    }
}
