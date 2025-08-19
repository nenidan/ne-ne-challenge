package com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto;

import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductCommand {

    private ProductId productId;
    private String productName;
    private String productDescription;
    private Integer productPrice;
}

