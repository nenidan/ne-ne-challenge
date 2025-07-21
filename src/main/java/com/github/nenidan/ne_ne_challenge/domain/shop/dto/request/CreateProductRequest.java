package com.github.nenidan.ne_ne_challenge.domain.shop.dto.request;

import com.github.nenidan.ne_ne_challenge.domain.shop.entity.Product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateProductRequest {

    private final String productName;
    private final String productDescription;
    private final Integer productPrice;

    public Product toEntity() {
        return new Product(productName, productDescription, productPrice);
    }
}