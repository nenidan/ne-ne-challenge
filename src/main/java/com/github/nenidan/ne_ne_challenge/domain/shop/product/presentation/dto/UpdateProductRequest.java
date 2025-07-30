package com.github.nenidan.ne_ne_challenge.domain.shop.product.presentation.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateProductRequest {

    private final String productName;
    private final String productDescription;
    private final Integer productPrice;
}

