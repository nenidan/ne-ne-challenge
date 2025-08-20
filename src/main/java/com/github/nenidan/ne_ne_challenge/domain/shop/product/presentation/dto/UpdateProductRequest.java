package com.github.nenidan.ne_ne_challenge.domain.shop.product.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequest {

    private String productName;
    private String productDescription;
    private Integer productPrice;
}

