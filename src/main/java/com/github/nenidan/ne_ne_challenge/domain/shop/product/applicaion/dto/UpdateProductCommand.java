package com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto;

import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateProductCommand {

    private final ProductId productId;
    private final String productName;
    private final String productDescription;
    private final Integer productPrice;
}

