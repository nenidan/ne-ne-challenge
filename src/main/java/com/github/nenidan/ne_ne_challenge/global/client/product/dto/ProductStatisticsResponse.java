package com.github.nenidan.ne_ne_challenge.global.client.product.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductStatisticsResponse {

    private final Long id;
    private final String name;
    private final String description;
    private final Integer price;
}
