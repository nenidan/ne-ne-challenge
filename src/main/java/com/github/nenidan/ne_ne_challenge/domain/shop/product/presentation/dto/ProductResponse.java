package com.github.nenidan.ne_ne_challenge.domain.shop.product.presentation.dto;

import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto.ProductResult;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductResponse {

    @Schema(description = "상품 식별자", example = "1")
    private final Long id;
    @Schema(description = "상품 이름", example = "GS25 5000 기프티콘")
    private final String name;
    @Schema(description = "상품 설명", example = "GS25에서 5000원 내 상품과 교환 가능합니다.")
    private final String description;
    @Schema(description = "상품 현재 가격", example = "5000")
    private final Integer price;

    public static ProductResponse from(ProductResult productResult) {
        return new ProductResponse(
            productResult.getId().getValue(),
            productResult.getName(),
            productResult.getDescription(),
            productResult.getPrice()
        );
    }
}
