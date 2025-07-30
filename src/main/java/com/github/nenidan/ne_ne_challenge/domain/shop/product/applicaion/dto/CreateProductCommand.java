package com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateProductCommand {

    private final String productName;
    private final String productDescription;
    private final Integer productPrice;
}