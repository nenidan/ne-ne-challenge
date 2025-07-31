package com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateProductRequest {

    @NotBlank(message = "상품명을 등록해야 합니다.")
    private final String productName;

    @NotBlank(message = "상품 설명을 등록해야 합니다.")
    private final String productDescription;

    @NotNull(message = "상품 가격을 등록해야 합니다.")
    @Positive(message = "상품 가격은 음수 일 수 없습니다.")
    private final Integer productPrice;
}